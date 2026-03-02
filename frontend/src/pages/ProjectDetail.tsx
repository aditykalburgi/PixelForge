import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import api from '../api/axios';
import { FileText, Users, Download, Upload } from 'lucide-react';
import { Button, Card, Separator } from '../components/ui';

interface ProjectDetailData {
    project: any;
    members: any[];
    documents: any[];
    availableUsers: any[];
}

const ProjectDetail: React.FC = () => {
    const { id } = useParams();
    const [data, setData] = useState<ProjectDetailData | null>(null);

    useEffect(() => {
        const fetchDetail = async () => {
            try {
                const res = await api.get(`/projects/${id}`);
                setData(res.data);
            } catch (err) {
                console.error(err);
            }
        };
        fetchDetail();
    }, [id]);

    if (!data) return <div className="px-6 md:px-12 py-8 font-serif text-center">Loading project...</div>;

    return (
        <div className="relative pattern-grid">
            {/* Hero Header */}
            <header className="px-6 md:px-12 py-12 md:py-16">
                <h1 className="font-display text-6xl md:text-7xl font-bold tracking-tighter leading-tight mb-8">
                    {data.project.name}
                </h1>
                
                {/* Description with Drop Cap */}
                <div className="max-w-3xl">
                    <p className="font-serif text-lg leading-relaxed text-muted-foreground drop-cap">
                        {data.project.description}
                    </p>
                </div>
            </header>

            <Separator thickness="thick" className="mx-6 md:mx-12" />

            {/* Content Grid */}
            <section className="px-6 md:px-12 py-12 md:py-16">
                <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
                    {/* Documents Section - Spans 2 columns on large screens */}
                    <div className="lg:col-span-2">
                        <Card variant="default">
                            <div className="flex items-center gap-3 mb-8">
                                <FileText size={28} strokeWidth={1.5} className="text-foreground flex-shrink-0" />
                                <h2 className="font-display text-3xl font-bold tracking-tight">
                                    Documents
                                </h2>
                            </div>

                            {data.documents.length === 0 ? (
                                <div className="text-center py-12">
                                    <p className="font-serif text-muted-foreground">No documents yet</p>
                                </div>
                            ) : (
                                <div className="space-y-1 mb-8">
                                    {data.documents.map((doc, index) => (
                                        <div key={doc.id}>
                                            <div className="flex items-center justify-between p-4 hover:bg-muted transition-colors duration-100">
                                                <span className="font-serif text-base">{doc.originalName}</span>
                                                <a
                                                    href={`http://localhost:8080/api/uploads/${doc.filename}`}
                                                    className="text-foreground hover:underline focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2"
                                                >
                                                    <Download size={20} strokeWidth={1.5} />
                                                </a>
                                            </div>
                                            {index < data.documents.length - 1 && (
                                                <div className="h-px bg-border-light" />
                                            )}
                                        </div>
                                    ))}
                                </div>
                            )}

                            {/* Upload Button */}
                            <div className="border-t border-foreground pt-6">
                                <Button
                                    variant="secondary"
                                    className="w-full justify-center gap-2"
                                >
                                    <Upload size={18} strokeWidth={1.5} />
                                    Upload Document
                                </Button>
                            </div>
                        </Card>
                    </div>

                    {/* Team Members Section */}
                    <div>
                        <Card variant="default">
                            <div className="flex items-center gap-3 mb-8">
                                <Users size={28} strokeWidth={1.5} className="text-foreground flex-shrink-0" />
                                <h2 className="font-display text-2xl font-bold tracking-tight">
                                    Team
                                </h2>
                            </div>

                            {data.members.length === 0 ? (
                                <div className="text-center py-8">
                                    <p className="font-serif text-muted-foreground text-sm">No members assigned</p>
                                </div>
                            ) : (
                                <div className="space-y-1">
                                    {data.members.map((member, index) => (
                                        <div key={member.id}>
                                            <div className="p-4">
                                                <p className="font-serif font-bold text-base mb-1">
                                                    {member.username || member.userId}
                                                </p>
                                                <p className="font-mono text-xs text-muted-foreground uppercase tracking-wide">
                                                    Team Member
                                                </p>
                                            </div>
                                            {index < data.members.length - 1 && (
                                                <div className="h-px bg-border-light" />
                                            )}
                                        </div>
                                    ))}
                                </div>
                            )}
                        </Card>
                    </div>
                </div>
            </section>
        </div>
    );
};

export default ProjectDetail;
