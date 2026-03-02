import React, { useState } from 'react';
import { useAuth } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';

const Login: React.FC = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const { login } = useAuth();
    const navigate = useNavigate();

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError('');
        try {
            const params = new URLSearchParams();
            params.append('username', username);
            params.append('password', password);
            await login(params);
            navigate('/dashboard');
        } catch (err: any) {
            setError('Invalid username or password');
        }
    };

    return (
        <div style={{ 
            minHeight: '100vh',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
            backgroundColor: '#FFFFFF',
            color: '#000000',
            fontFamily: '"Source Serif 4", Georgia, serif'
        }}>
            <div style={{ width: '100%', maxWidth: '400px', padding: '2rem' }}>
                {/* Header */}
                <div style={{ textAlign: 'center', marginBottom: '3rem' }}>
                    <h1 style={{ 
                        fontSize: '3rem',
                        fontWeight: 'bold',
                        marginBottom: '1rem',
                        fontFamily: '"Playfair Display", Georgia, serif',
                        letterSpacing: '-0.025em'
                    }}>
                        PixelForge
                    </h1>
                    <p style={{ fontSize: '1.125rem', color: '#525252' }}>
                        Nexus Platform
                    </p>
                </div>

                {/* Error Message */}
                {error && (
                    <div style={{
                        backgroundColor: '#000000',
                        color: '#FFFFFF',
                        padding: '1rem',
                        marginBottom: '1.5rem',
                        border: '1px solid #000000',
                        fontSize: '0.875rem'
                    }}>
                        {error}
                    </div>
                )}

                {/* Form Card */}
                <div style={{ 
                    backgroundColor: '#FFFFFF',
                    border: '1px solid #000000',
                    padding: '2rem'
                }}>
                    <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '2rem' }}>
                        {/* Username Input */}
                        <div>
                            <label style={{
                                display: 'block',
                                marginBottom: '0.5rem',
                                fontSize: '0.75rem',
                                fontWeight: '600',
                                letterSpacing: '0.1em',
                                textTransform: 'uppercase',
                                fontFamily: '"JetBrains Mono", monospace'
                            }}>
                                Username
                            </label>
                            <input
                                type="text"
                                value={username}
                                onChange={(e) => setUsername(e.target.value)}
                                required
                                placeholder="Enter your username"
                                style={{
                                    width: '100%',
                                    borderBottom: '2px solid #000000',
                                    backgroundColor: '#FFFFFF',
                                    color: '#000000',
                                    padding: '0.75rem 0',
                                    fontSize: '1rem',
                                    fontFamily: 'inherit',
                                    outline: 'none',
                                    boxSizing: 'border-box'
                                }}
                                onFocus={(e) => e.currentTarget.style.borderBottomWidth = '4px'}
                                onBlur={(e) => e.currentTarget.style.borderBottomWidth = '2px'}
                            />
                        </div>

                        {/* Password Input */}
                        <div>
                            <label style={{
                                display: 'block',
                                marginBottom: '0.5rem',
                                fontSize: '0.75rem',
                                fontWeight: '600',
                                letterSpacing: '0.1em',
                                textTransform: 'uppercase',
                                fontFamily: '"JetBrains Mono", monospace'
                            }}>
                                Password
                            </label>
                            <input
                                type="password"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                required
                                placeholder="Enter your password"
                                style={{
                                    width: '100%',
                                    borderBottom: '2px solid #000000',
                                    backgroundColor: '#FFFFFF',
                                    color: '#000000',
                                    padding: '0.75rem 0',
                                    fontSize: '1rem',
                                    fontFamily: 'inherit',
                                    outline: 'none',
                                    boxSizing: 'border-box'
                                }}
                                onFocus={(e) => e.currentTarget.style.borderBottomWidth = '4px'}
                                onBlur={(e) => e.currentTarget.style.borderBottomWidth = '2px'}
                            />
                        </div>

                        {/* Submit Button */}
                        <button
                            type="submit"
                            style={{
                                backgroundColor: '#000000',
                                color: '#FFFFFF',
                                padding: '1rem',
                                fontSize: '0.875rem',
                                fontWeight: '600',
                                letterSpacing: '0.1em',
                                textTransform: 'uppercase',
                                cursor: 'pointer',
                                border: 'none',
                                fontFamily: 'inherit',
                                transition: 'all 100ms'
                            }}
                            onMouseEnter={(e) => {
                                e.currentTarget.style.backgroundColor = '#FFFFFF';
                                e.currentTarget.style.color = '#000000';
                                e.currentTarget.style.border = '2px solid #000000';
                            }}
                            onMouseLeave={(e) => {
                                e.currentTarget.style.backgroundColor = '#000000';
                                e.currentTarget.style.color = '#FFFFFF';
                                e.currentTarget.style.border = 'none';
                            }}
                        >
                            Sign In
                        </button>
                    </form>
                </div>
            </div>
        </div>
    );
};

export default Login;
