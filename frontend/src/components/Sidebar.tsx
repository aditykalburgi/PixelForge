import React from 'react';
import { NavLink } from 'react-router-dom';
import { LayoutDashboard, Briefcase, User, Settings, LogOut } from 'lucide-react';
import { useAuth } from '../context/AuthContext';

const Sidebar: React.FC = () => {
    const { logout, user } = useAuth();

    const navItems = [
        { to: '/dashboard', icon: <LayoutDashboard size={20} />, label: 'Dashboard' },
        { to: '/projects', icon: <Briefcase size={20} />, label: 'Projects' },
        { to: '/account', icon: <User size={20} />, label: 'Account' },
    ];

    if (user?.roles.includes('ADMIN')) {
        navItems.push({ to: '/admin/users', icon: <Settings size={20} />, label: 'Admin' });
    }

    return (
        <div className="glass" style={{ width: '260px', height: '100vh', padding: '1.5rem', display: 'flex', flexDirection: 'column', position: 'fixed', left: 0, top: 0, borderRadius: 0 }}>
            <div style={{ padding: '0 0.5rem 2rem' }}>
                <h2 style={{ fontSize: '1.25rem', fontWeight: 'bold', color: 'var(--primary)' }}>PixelForge</h2>
            </div>

            <nav style={{ flex: 1 }}>
                {navItems.map((item) => (
                    <NavLink
                        key={item.to}
                        to={item.to}
                        style={({ isActive }) => ({
                            display: 'flex',
                            alignItems: 'center',
                            gap: '0.75rem',
                            padding: '0.75rem 1rem',
                            marginBottom: '0.5rem',
                            borderRadius: '8px',
                            color: isActive ? 'white' : 'var(--text-muted)',
                            backgroundColor: isActive ? 'rgba(59, 130, 246, 0.2)' : 'transparent',
                            transition: 'all 0.2s'
                        })}
                    >
                        {item.icon}
                        <span style={{ fontWeight: 500 }}>{item.label}</span>
                    </NavLink>
                ))}
            </nav>

            <button
                onClick={logout}
                style={{
                    display: 'flex',
                    alignItems: 'center',
                    gap: '0.75rem',
                    padding: '0.75rem 1rem',
                    color: 'var(--error)',
                    marginTop: 'auto',
                    borderRadius: '8px',
                    transition: 'background 0.2s'
                }}
                onMouseOver={(e) => (e.currentTarget.style.backgroundColor = 'rgba(239, 68, 68, 0.1)')}
                onMouseOut={(e) => (e.currentTarget.style.backgroundColor = 'transparent')}
            >
                <LogOut size={20} />
                <span style={{ fontWeight: 500 }}>Logout</span>
            </button>
        </div>
    );
};

export default Sidebar;
