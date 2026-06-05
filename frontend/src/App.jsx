import { useState } from 'react'
import './App.css'

function App() {
  const [selectedFile, setSelectedFile] = useState(null)
  const [isUploading, setIsUploading] = useState(false)

  const handleFileChange = (e) => {
    if (e.target.files && e.target.files.length > 0) {
      setSelectedFile(e.target.files[0])
    }
  }

  const handleUpload = () => {
    if (!selectedFile) return
    setIsUploading(true)
    setTimeout(() => {
      setIsUploading(false)
      alert(`Resume "${selectedFile.name}" analyzed successfully! (Demo Mode)`)
    }, 1500)
  }

  return (
    <div id="center">
      <header style={{ margin: '40px 0 20px 0' }}>
        <h1 style={{ margin: '0 0 10px 0', fontSize: '3rem', background: 'linear-gradient(135deg, var(--accent), #e879f9)', WebkitBackgroundClip: 'text', WebkitTextFillColor: 'transparent' }}>
          ResumeRadar
        </h1>
        <p style={{ color: 'var(--text)', fontSize: '1.1rem' }}>
          AI-Powered Resume Analysis & Job Matching
        </p>
      </header>

      <main style={{ maxWidth: '600px', width: '100%', margin: '0 auto', display: 'flex', flexDirection: 'column', gap: '30px' }}>
        {/* Upload Container */}
        <div style={{
          border: '2px dashed var(--border)',
          borderRadius: '12px',
          padding: '40px 20px',
          background: 'var(--social-bg)',
          transition: 'all 0.3s ease',
          display: 'flex',
          flexDirection: 'column',
          alignItems: 'center',
          gap: '15px'
        }}>
          <div style={{ fontSize: '3rem' }}>📁</div>
          <h3 style={{ margin: 0, color: 'var(--text-h)' }}>Upload your Resume</h3>
          <p style={{ fontSize: '0.9rem', color: 'var(--text)' }}>Supports PDF, DOCX, or TXT formats</p>
          
          <input 
            type="file" 
            accept=".pdf,.docx,.txt" 
            onChange={handleFileChange} 
            style={{ display: 'none' }} 
            id="resume-upload-input" 
          />
          <label 
            htmlFor="resume-upload-input" 
            style={{
              cursor: 'pointer',
              padding: '10px 20px',
              backgroundColor: 'var(--accent-bg)',
              color: 'var(--accent)',
              border: '1px solid var(--accent-border)',
              borderRadius: '8px',
              fontWeight: '600',
              fontSize: '0.9rem',
              transition: 'background-color 0.2s'
            }}
          >
            {selectedFile ? 'Change File' : 'Select File'}
          </label>

          {selectedFile && (
            <div style={{ marginTop: '10px', display: 'flex', flexDirection: 'column', alignItems: 'center', gap: '10px' }}>
              <span style={{ fontSize: '0.95rem', color: 'var(--text-h)', fontWeight: 'bold' }}>
                Selected: {selectedFile.name}
              </span>
              <button 
                onClick={handleUpload}
                disabled={isUploading}
                style={{
                  padding: '10px 24px',
                  backgroundColor: 'var(--accent)',
                  color: 'white',
                  border: 'none',
                  borderRadius: '8px',
                  fontWeight: '600',
                  cursor: 'pointer',
                  opacity: isUploading ? 0.7 : 1,
                  transition: 'opacity 0.2s'
                }}
              >
                {isUploading ? 'Analyzing...' : 'Start AI Analysis'}
              </button>
            </div>
          )}
        </div>

        {/* Feature Cards Grid */}
        <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '20px', textAlign: 'left' }}>
          <div style={{ padding: '20px', border: '1px solid var(--border)', borderRadius: '8px', background: 'var(--bg)' }}>
            <h4 style={{ margin: '0 0 8px 0', color: 'var(--text-h)' }}>⚡ ATS Score Tuning</h4>
            <p style={{ fontSize: '0.85rem', color: 'var(--text)' }}>Optimize your resume keywords to beat applicant tracking systems.</p>
          </div>
          <div style={{ padding: '20px', border: '1px solid var(--border)', borderRadius: '8px', background: 'var(--bg)' }}>
            <h4 style={{ margin: '0 0 8px 0', color: 'var(--text-h)' }}>🎯 Skills Gap Analysis</h4>
            <p style={{ fontSize: '0.85rem', color: 'var(--text)' }}>Find matches with high-paying Java & Spring Boot roles instantly.</p>
          </div>
        </div>
      </main>
      
      <footer style={{ marginTop: '40px', padding: '20px 0', borderTop: '1px solid var(--border)', fontSize: '0.85rem', color: 'var(--text)' }}>
        ResumeRadar &copy; 2026. Made with ❤️ for job search acceleration.
      </footer>
    </div>
  )
}

export default App
