import React from 'react';
import Sidebar from './Sidebar';

const Layout: React.FC<{ children: React.ReactNode }> = ({ children }) => {
    return (
        <div className="flex min-h-screen bg-background pattern-lines">
            <Sidebar />
            <main className="flex-1 ml-72 bg-background">
                {children}
            </main>
        </div>
    );
};

export default Layout;
