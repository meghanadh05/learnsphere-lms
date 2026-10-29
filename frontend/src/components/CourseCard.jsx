import { Link } from 'react-router-dom'
import { Icon } from './Icons'

export default function CourseCard({ course }) {
  return <article className={`course-card accent-${course.accent}`}>
    <div className="course-art"><span>{course.code}</span><div className="art-orbit"/><div className="art-dot"/></div>
    <div className="course-card-body">
      <div className="eyebrow-row"><span>{course.category}</span><span>{course.level}</span></div>
      <h3>{course.title}</h3>
      <p>{course.description}</p>
      <div className="course-meta"><span><Icon name="clock" size={17}/>{course.durationHours} hours</span><span><Icon name="layers" size={17}/>{course.lessonCount} lessons</span></div>
      {course.enrolled && <div className="mini-progress"><div><span>Progress</span><strong>{course.progressPercent}%</strong></div><div className="progress-track"><span style={{ width: `${course.progressPercent}%` }}/></div></div>}
      <Link className="card-link" to={`/courses/${course.id}`}>{course.enrolled ? 'Continue learning' : 'View course'} <Icon name="arrow" size={18}/></Link>
    </div>
  </article>
}

