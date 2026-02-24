import React from 'react';
import Sidebar from './Sidebar';

const Layout: React.FC<{ children: React.ReactNode }> = ({ children }) => {
    return (
        <div style={{ display: 'flex', minHeight: '100vh' }}>
            <Sidebar />
            <main style={{ flex: 1, marginLeft: '260px', backgroundColor: 'var(--bg-dark)' }}>
                {children}
            </main>
        </div>
    );
};

export default Layout;
