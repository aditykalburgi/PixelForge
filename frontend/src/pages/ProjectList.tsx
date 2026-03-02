import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../api/axios';
import { Briefcase, ArrowRight, Clock, CheckCircle } from 'lucide-react';
import { Card, Separator } from '../components/ui';

interface Project {
    id: string;
    name: string;
    description: string;
    status: string;
}

const ProjectList: React.FC = () => {
    const [projects, setProjects] = useState<Project[]>([]);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchProjects = async () => {
            try {
                const res = await api.get('/projects');
                setProjects(res.data);
            } catch (err) {
                console.error(err);
            }
        };
        fetchProjects();
    }, []);

    return (
        <div className="relative pattern-lines">
            {/* Header */}
            <header className="px-6 md:px-12 py-12 md:py-16">
                <h1 className="font-display text-5xl md:text-6xl font-bold tracking-tighter">
                    All Projects
                </h1>
                <p className="font-serif text-lg text-muted-foreground mt-4">
                    {projects.length} {projects.length === 1 ? 'project' : 'projects'} total
                </p>
            </header>

            <Separator thickness="thick" className="mx-6 md:mx-12" />

            {/* Projects List */}
            <section className="px-6 md:px-12 py-12 md:py-16">
                {projects.length === 0 ? (
                    <div className="text-center py-24">
                        <Briefcase size={64} strokeWidth={1} className="mx-auto mb-6 text-muted-foreground" />
                        <p className="font-serif text-lg text-muted-foreground">No projects available</p>
                    </div>
                ) : (
                    <div className="space-y-6">
                        {projects.map((project, index) => (
                            <React.Fragment key={project.id}>
                                <button
                                    onClick={() => navigate(`/projects/${project.id}`)}
                                    className="w-full text-left group"
                                >
                                    <Card variant="borderless" className="p-0 transition-all duration-100 hover:bg-muted">
                                        <div className="flex items-start justify-between gap-6">
                                            {/* Content */}
                                            <div className="flex-1">
                                                <div className="flex items-center gap-3 mb-2">
                                                    <Briefcase size={24} strokeWidth={1.5} className="text-foreground flex-shrink-0" />
                                                    <h3 className="font-display text-3xl font-bold tracking-tight leading-tight group-hover:underline transition-all">
                                                        {project.name}
                                                    </h3>
                                                </div>
                                                <p className="font-serif text-base text-muted-foreground leading-relaxed mb-4">
                                                    {project.description}
                                                </p>
                                                {/* Status */}
                                                <div className="flex items-center gap-2">
                                                    {project.status === 'COMPLETED' ? (
                                                        <CheckCircle size={16} strokeWidth={1.5} className="text-foreground" />
                                                    ) : (
                                                        <Clock size={16} strokeWidth={1.5} className="text-foreground" />
                                                    )}
                                                    <span className="font-mono text-xs uppercase tracking-widest font-medium">
                                                        {project.status}
                                                    </span>
                                                </div>
                                            </div>

                                            {/* Arrow */}
                                            <div className="flex-shrink-0 flex items-center justify-center">
                                                <ArrowRight
                                                    size={24}
                                                    strokeWidth={1.5}
                                                    className="text-foreground group-hover:translate-x-1 transition-transform duration-100"
                                                />
                                            </div>
                                        </div>
                                    </Card>
                                </button>

                                {/* Horizontal divider between items */}
                                {index < projects.length - 1 && (
                                    <div className="h-px bg-border-light" />
                                )}
                            </React.Fragment>
                        ))}
                    </div>
                )}
            </section>
        </div>
    );
};

export default ProjectList;
