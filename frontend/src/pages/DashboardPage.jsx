import { useEffect, useState } from 'react'
import { Link, Navigate } from 'react-router-dom'
import { useAuth } from '../context/AuthContext'
import { api } from '../services/api'
import { Icon } from '../components/Icons'

export default function DashboardPage() {
  const { user, loading } = useAuth(); const [data, setData] = useState(null); const [error, setError] = useState('')
  useEffect(() => { if (user) api('/dashboard').then(setData).catch(e => setError(e.message)) }, [user])
  if (!loading && !user) return <Navigate to="/login" replace/>
  if (!data) return <div className="page-message page-top">{error || 'Loading your dashboard…'}</div>
  return <section className="dashboard section-pad page-top"><div className="dashboard-heading"><div><p className="kicker"><span/> My learning</p><h1>Good to see you,<br/><em>{data.user.name.split(' ')[0]}.</em></h1></div><p>Here is the clearest view of where you are—and what is worth doing next.</p></div><div className="stat-grid"><article><span><Icon name="book"/></span><strong>{data.enrollments.length}</strong><p>Courses enrolled</p></article><article><span><Icon name="chart"/></span><strong>{data.coursesInProgress}</strong><p>In progress</p></article><article><span><Icon name="check"/></span><strong>{data.coursesCompleted}</strong><p>Completed</p></article><article><span><Icon name="trophy"/></span><strong>{data.averageQuizScore}%</strong><p>Average quiz score</p></article></div><div className="dashboard-list-head"><h2>Your courses</h2><Link to="/courses">Browse all <Icon name="arrow" size={17}/></Link></div>{data.enrollments.length ? <div className="dashboard-courses">{data.enrollments.map(item => <article className={`accent-${item.accent}`} key={item.courseId}><div className="dashboard-course-code">{item.code}</div><div className="dashboard-course-info"><p>{item.instructor}</p><h3>{item.title}</h3><div className="progress-track"><span style={{width: `${item.progressPercent}%`}}/></div><div className="course-progress-label"><span>{item.progressPercent}% complete</span><span>{item.bestQuizScore == null ? 'Quiz not attempted' : `Best quiz: ${item.bestQuizScore}%`}</span></div></div><Link className="round-link" to={`/courses/${item.courseId}`} aria-label={`Continue ${item.title}`}><Icon name="arrow"/></Link></article>)}</div> : <div className="empty-state"><h3>Your learning path starts here.</h3><p>Choose a course and your progress will appear on this dashboard.</p><Link className="button" to="/courses">Explore courses</Link></div>}</section>
}

