import React, { createContext, useContext, useState, useEffect } from 'react';
import api from '../api/axios';

interface User {
    username: string;
    roles: string[];
    authenticated: boolean;
}

interface AuthContextType {
    user: User | null;
    loading: boolean;
    login: (credentials: URLSearchParams) => Promise<void>;
    logout: () => Promise<void>;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
    const [user, setUser] = useState<User | null>(null);
    const [loading, setLoading] = useState(true);

    const checkAuth = async () => {
        try {
            const controller = new AbortController();
            const timeoutId = setTimeout(() => controller.abort(), 5000); // 5 second timeout
            
            const response = await api.get('/auth/me', {
                signal: controller.signal
            });
            clearTimeout(timeoutId);
            
            if (response.data.authenticated) {
                setUser(response.data);
            } else {
                setUser(null);
            }
        } catch (error) {
            // Silently fail - user will be redirected to login
            console.warn('Auth check failed:', error instanceof Error ? error.message : 'Unknown error');
            setUser(null);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        checkAuth();
    }, []);

    const login = async (credentials: URLSearchParams) => {
        // Spring Security form login is at /login (not /api/login)
        // Use absolute URL since login endpoint is outside /api base path
        await api.post('http://localhost:8080/login', credentials, {
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
        });
        await checkAuth();
    };

    const logout = async () => {
        await api.post('/logout');
        setUser(null);
    };

    return (
        <AuthContext.Provider value={{ user, loading, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = () => {
    const context = useContext(AuthContext);
    if (context === undefined) {
        throw new Error('useAuth must be used within an AuthProvider');
    }
    return context;
};
