import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider, useAuth } from './context/AuthContext';
import Login from './pages/Login';
import Layout from './components/Layout';
import Dashboard from './pages/Dashboard';
import ProjectList from './pages/ProjectList';
import ProjectDetail from './pages/ProjectDetail';

const ProtectedRoute: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const { user, loading } = useAuth();

  if (loading) return <div className="flex items-center justify-center min-h-screen bg-background pattern-lines"><span className="text-lg font-serif">Loading...</span></div>;
  if (!user) return <Navigate to="/login" />;

  return <>{children}</>;
};

function App() {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route
            path="/*"
            element={
              <ProtectedRoute>
                <Layout>
                  <Routes>
                    <Route path="dashboard" element={<Dashboard />} />
                    <Route path="projects" element={<ProjectList />} />
                    <Route path="projects/:id" element={<ProjectDetail />} />
                    <Route path="account" element={<div className="p-8 md:p-12">Account Component (Coming Soon)</div>} />
                    <Route path="admin/users" element={<div className="p-8 md:p-12">Admin Component (Coming Soon)</div>} />
                    <Route path="" element={<Navigate to="/dashboard" />} />
                  </Routes>
                </Layout>
              </ProtectedRoute>
            }
          />
        </Routes>
      </Router>
    </AuthProvider>
  );
}

export default App;
