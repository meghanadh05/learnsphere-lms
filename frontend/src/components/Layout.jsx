import { useState } from 'react'
import { Link, NavLink, useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'
import { Icon } from './Icons'

export default function Layout({ children }) {
  const [open, setOpen] = useState(false)
  const { user, logout } = useAuth()
  const navigate = useNavigate()
  const signOut = () => { logout(); setOpen(false); navigate('/') }
  return <div className="app-shell">
    <header className="site-header">
      <Link className="brand" to="/" onClick={() => setOpen(false)}><span className="brand-mark">L</span><span>LearnSphere</span></Link>
      <button className="menu-button" onClick={() => setOpen(!open)} aria-label="Toggle navigation"><Icon name={open ? 'close' : 'menu'} /></button>
      <nav className={open ? 'nav open' : 'nav'}>
        <NavLink to="/courses" onClick={() => setOpen(false)}>Explore courses</NavLink>
        {user && <NavLink to="/dashboard" onClick={() => setOpen(false)}>My learning</NavLink>}
        <a href="/#team" onClick={() => setOpen(false)}>Our team</a>
        {user ? <div className="user-actions"><span className="user-chip">{user.name.split(' ')[0]}</span><button className="text-button" onClick={signOut}>Sign out</button></div>
          : <div className="nav-auth"><Link to="/login" onClick={() => setOpen(false)}>Sign in</Link><Link className="button button-small" to="/register" onClick={() => setOpen(false)}>Get started</Link></div>}
      </nav>
    </header>
    <main>{children}</main>
    <footer><div><Link className="brand footer-brand" to="/"><span className="brand-mark">L</span><span>LearnSphere</span></Link><p>Focused learning. Measurable progress.</p></div><div className="footer-links"><Link to="/courses">Courses</Link><a href="/#team">Team</a><span>React · Spring MVC · MySQL</span></div></footer>
  </div>
}
