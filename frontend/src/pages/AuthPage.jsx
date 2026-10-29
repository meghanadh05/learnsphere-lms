import { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'

export default function AuthPage({ mode }) {
  const isRegister = mode === 'register'
  const { login, register } = useAuth()
  const navigate = useNavigate()
  const [values, setValues] = useState(isRegister ? { name: '', email: '', password: '' } : { email: 'student@learnsphere.demo', password: 'Demo@123' })
  const [error, setError] = useState(''); const [busy, setBusy] = useState(false)
  const submit = async (event) => { event.preventDefault(); setBusy(true); setError(''); try { await (isRegister ? register(values) : login(values)); navigate('/dashboard') } catch (e) { setError(e.message) } finally { setBusy(false) } }
  return <section className="auth-page page-top"><div className="auth-aside"><p className="kicker light"><span/> LearnSphere</p><blockquote>“Small, focused steps turn curiosity into capability.”</blockquote><p>Track every course, quiz, and milestone in one place.</p></div><div className="auth-panel"><div className="auth-form-wrap"><p className="kicker"><span/> {isRegister ? 'Start learning' : 'Welcome back'}</p><h1>{isRegister ? 'Create your account.' : 'Continue your path.'}</h1><p>{isRegister ? 'Join the platform and begin your first course.' : 'Sign in to see your courses and progress.'}</p>{error && <div className="notice error">{error}</div>}<form onSubmit={submit}>{isRegister && <label>Full name<input required maxLength="80" value={values.name} onChange={e => setValues({...values, name: e.target.value})} placeholder="Your full name"/></label>}<label>Email address<input type="email" required value={values.email} onChange={e => setValues({...values, email: e.target.value})} placeholder="you@example.com"/></label><label>Password<input type="password" required minLength="8" value={values.password} onChange={e => setValues({...values, password: e.target.value})} placeholder="At least 8 characters"/></label><button className="button auth-submit" disabled={busy}>{busy ? 'Please wait…' : isRegister ? 'Create account' : 'Sign in'}</button></form><p className="auth-switch">{isRegister ? 'Already have an account?' : 'New to LearnSphere?'} <Link to={isRegister ? '/login' : '/register'}>{isRegister ? 'Sign in' : 'Create an account'}</Link></p></div></div></section>
}
