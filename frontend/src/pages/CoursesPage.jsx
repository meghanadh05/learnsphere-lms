import { useEffect, useMemo, useState } from 'react'
import CourseCard from '../components/CourseCard'
import { api } from '../services/api'

export default function CoursesPage() {
  const [courses, setCourses] = useState([])
  const [query, setQuery] = useState('')
  const [category, setCategory] = useState('All')
  const [error, setError] = useState('')
  useEffect(() => { api('/courses').then(setCourses).catch(e => setError(e.message)) }, [])
  const categories = ['All', ...new Set(courses.map(c => c.category))]
  const filtered = useMemo(() => courses.filter(c => (category === 'All' || c.category === category) && `${c.title} ${c.description} ${c.instructor}`.toLowerCase().includes(query.toLowerCase())), [courses, category, query])
  return <section className="catalog section-pad page-top">
    <div className="catalog-heading"><div><p className="kicker"><span/> Course library</p><h1>Find your next<br/><em>learning path.</em></h1></div><p>Practical courses designed to take you from concept to confident application.</p></div>
    <div className="catalog-tools"><div className="category-tabs">{categories.map(item => <button className={item === category ? 'active' : ''} key={item} onClick={() => setCategory(item)}>{item}</button>)}</div><label className="search"><span>Search</span><input value={query} onChange={e => setQuery(e.target.value)} placeholder="Course or instructor…"/></label></div>
    {error && <div className="notice error">{error}</div>}
    {!error && !courses.length && <div className="loading-grid">Loading the course library…</div>}
    <div className="course-grid">{filtered.map(course => <CourseCard course={course} key={course.id}/>)}</div>
    {courses.length > 0 && !filtered.length && <div className="empty-state"><h3>No matching courses</h3><p>Try a different search or category.</p></div>}
  </section>
}

