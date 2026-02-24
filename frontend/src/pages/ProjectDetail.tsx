import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import api from '../api/axios';
import { FileText, Users, Download, Upload } from 'lucide-react';

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

    if (!data) return <div style={{ padding: '2rem' }}>Loading project...</div>;

    return (
        <div style={{ padding: '2rem' }}>
            <h1 style={{ fontSize: '1.875rem', fontWeight: 'bold', marginBottom: '0.5rem' }}>{data.project.name}</h1>
            <p style={{ color: 'var(--text-muted)', marginBottom: '2rem' }}>{data.project.description}</p>

            <div style={{ display: 'grid', gridTemplateColumns: '2fr 1fr', gap: '2rem' }}>
                <div>
                    <section className="glass" style={{ padding: '1.5rem', marginBottom: '2rem' }}>
                        <h2 style={{ fontSize: '1.25rem', marginBottom: '1.5rem', display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                            <FileText size={20} /> Documents
                        </h2>
                        {data.documents.map((doc) => (
                            <div key={doc.id} style={{ display: 'flex', justifyContent: 'space-between', padding: '1rem', borderBottom: '1px solid var(--border)' }}>
                                <span>{doc.originalName}</span>
                                <a href={`http://localhost:8080/api/uploads/${doc.filename}`} style={{ color: 'var(--primary)' }}>
                                    <Download size={18} />
                                </a>
                            </div>
                        ))}
                        <div style={{ marginTop: '1.5rem' }}>
                            <button className="glass" style={{ width: '100%', padding: '0.75rem', borderStyle: 'dashed', color: 'var(--text-muted)', display: 'flex', alignItems: 'center', justifyContent: 'center', gap: '0.5rem' }}>
                                <Upload size={18} /> Upload New Document
                            </button>
                        </div>
                    </section>
                </div>

                <div>
                    <section className="glass" style={{ padding: '1.5rem' }}>
                        <h2 style={{ fontSize: '1.125rem', marginBottom: '1.5rem', display: 'flex', alignItems: 'center', gap: '0.5rem' }}>
                            <Users size={20} /> Team Members
                        </h2>
                        {data.members.map((member) => (
                            <div key={member.id} style={{ marginBottom: '1rem' }}>
                                <p style={{ fontWeight: 500 }}>{member.username || member.userId}</p>
                                <p style={{ fontSize: '0.75rem', color: 'var(--text-muted)' }}>Assigned by Admin</p>
                            </div>
                        ))}
                    </section>
                </div>
            </div>
        </div>
    );
};

export default ProjectDetail;
