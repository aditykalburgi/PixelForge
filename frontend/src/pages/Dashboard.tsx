import React, { useEffect, useState } from 'react';
import api from '../api/axios';
import { useAuth } from '../context/AuthContext';
import { Briefcase, Plus, Clock, CheckCircle } from 'lucide-react';
import { Button, Card, Separator } from '../components/ui';

interface Project {
    id: string;
    name: string;
    description: string;
    status: string;
    createdAt: string;
}

const Dashboard: React.FC = () => {
    const [projects, setProjects] = useState<Project[]>([]);
    const [loading, setLoading] = useState(true);
    const { user } = useAuth();

    useEffect(() => {
        const fetchDashboard = async () => {
            try {
                const response = await api.get('/dashboard');
                setProjects(response.data.projects);
            } catch (error) {
                console.error('Error fetching dashboard:', error);
            } finally {
                setLoading(false);
            }
        };
        fetchDashboard();
    }, []);

    if (loading) return <div className="px-6 md:px-12 py-8 font-serif text-center">Loading dashboard...</div>;

    return (
        <div className="relative pattern-lines">
            {/* Header Section */}
            <header className="px-6 md:px-12 py-12 md:py-16">
                <div className="flex flex-col md:flex-row md:items-end md:justify-between gap-8">
                    <div>
                        <h1 className="font-display text-5xl md:text-6xl font-bold tracking-tighter mb-4">
                            Dashboard
                        </h1>
                        <p className="font-serif text-lg text-muted-foreground">
                            Welcome, <span className="font-bold text-foreground">{user?.username}</span>
                        </p>
                        <p className="font-serif text-sm text-muted-foreground mt-2">
                            {projects.length} {projects.length === 1 ? 'project' : 'projects'} in your workspace
                        </p>
                    </div>
                    {user?.roles.includes('ROLE_ADMIN') && (
                        <Button variant="primary" size="lg">
                            <Plus size={18} strokeWidth={2} className="inline mr-2" />
                            New Project
                        </Button>
                    )}
                </div>
            </header>

            {/* Separator */}
            <Separator thickness="thick" className="mx-6 md:mx-12" />

            {/* Projects Grid */}
            <section className="px-6 md:px-12 py-12 md:py-16">
                {projects.length === 0 ? (
                    <div className="text-center py-16">
                        <Briefcase size={64} strokeWidth={1} className="mx-auto mb-6 text-muted-foreground" />
                        <p className="font-serif text-lg text-muted-foreground">No projects yet</p>
                    </div>
                ) : (
                    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
                        {projects.map((project) => (
                            <Card key={project.id} variant="default" className="group transition-all duration-100 hover:bg-foreground hover:text-background">
                                {/* Project Icon & Title */}
                                <div className="flex items-start gap-4 mb-4">
                                    <div className="flex-shrink-0">
                                        <Briefcase size={28} strokeWidth={1.5} className="text-foreground group-hover:text-background" />
                                    </div>
                                    <h3 className="font-display text-2xl font-bold tracking-tight leading-tight">
                                        {project.name}
                                    </h3>
                                </div>

                                {/* Description */}
                                <p className="font-serif text-base leading-relaxed mb-6 line-clamp-3">
                                    {project.description}
                                </p>

                                {/* Footer: Status & Date */}
                                <div className="flex items-center justify-between pt-4 border-t border-inherit">
                                    {/* Status Badge */}
                                    <div className="flex items-center gap-2">
                                        {project.status === 'COMPLETED' ? (
                                            <CheckCircle size={16} strokeWidth={1.5} className="text-foreground group-hover:text-background" />
                                        ) : (
                                            <Clock size={16} strokeWidth={1.5} className="text-foreground group-hover:text-background" />
                                        )}
                                        <span className="font-mono text-xs uppercase tracking-widest font-medium">
                                            {project.status}
                                        </span>
                                    </div>

                                    {/* Date */}
                                    <span className="font-mono text-xs text-muted-foreground group-hover:text-inherit transition-colors">
                                        {new Date(project.createdAt).toLocaleDateString()}
                                    </span>
                                </div>
                            </Card>
                        ))}
                    </div>
                )}
            </section>
        </div>
    );
};

export default Dashboard;
