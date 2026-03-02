import React from 'react';
import { NavLink } from 'react-router-dom';
import { LayoutDashboard, Briefcase, User, Settings, LogOut } from 'lucide-react';
import { useAuth } from '../context/AuthContext';

const Sidebar: React.FC = () => {
    const { logout, user } = useAuth();

    const navItems = [
        { to: '/dashboard', icon: <LayoutDashboard size={20} strokeWidth={1.5} />, label: 'Dashboard' },
        { to: '/projects', icon: <Briefcase size={20} strokeWidth={1.5} />, label: 'Projects' },
        { to: '/account', icon: <User size={20} strokeWidth={1.5} />, label: 'Account' },
    ];

    if (user?.roles.includes('ADMIN')) {
        navItems.push({ to: '/admin/users', icon: <Settings size={20} strokeWidth={1.5} />, label: 'Admin' });
    }

    return (
        <div className="fixed left-0 top-0 w-72 h-screen bg-background border-r border-foreground flex flex-col pattern-lines">
            {/* Header */}
            <div className="p-8 border-b border-foreground">
                <h2 className="font-display text-2xl font-bold tracking-tight">PixelForge</h2>
            </div>

            {/* Navigation */}
            <nav className="flex-1 overflow-y-auto">
                <ul className="space-y-px p-6">
                    {navItems.map((item) => (
                        <li key={item.to}>
                            <NavLink
                                to={item.to}
                                className={({ isActive }) =>
                                    `flex items-center gap-3 px-4 py-3 text-sm font-medium transition-all duration-100 border-l-[3px] ${
                                        isActive
                                            ? 'border-l-foreground bg-muted text-foreground'
                                            : 'border-l-transparent text-muted-foreground hover:text-foreground'
                                    }`
                                }
                            >
                                {item.icon}
                                <span>{item.label}</span>
                            </NavLink>
                        </li>
                    ))}
                </ul>
            </nav>

            {/* Logout */}
            <div className="border-t border-foreground p-6">
                <button
                    onClick={logout}
                    className="flex items-center gap-3 px-4 py-3 text-sm font-medium text-muted-foreground w-full transition-all duration-100 hover:text-foreground focus-visible:outline focus-visible:outline-3 focus-visible:outline-offset-2"
                >
                    <LogOut size={20} strokeWidth={1.5} />
                    <span>Logout</span>
                </button>
            </div>
        </div>
    );
};

export default Sidebar;
