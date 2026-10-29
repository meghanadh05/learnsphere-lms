import { Link } from 'react-router-dom'
import { teamMembers } from '../data'
import { Icon } from '../components/Icons'

export default function HomePage() {
  return <>
    <section className="hero section-pad">
      <div className="hero-copy">
        <p className="kicker"><span/> Learn with direction</p>
        <h1>Build skills that<br/><em>move you forward.</em></h1>
        <p className="hero-lead">A focused learning space for modern technology—clear courses, practical quizzes, and progress you can see.</p>
        <div className="hero-actions"><Link className="button" to="/courses">Explore courses <Icon name="arrow"/></Link><Link className="link-button" to="/login"><span className="play-circle"><Icon name="play" size={16}/></span> Try the student demo</Link></div>
        <div className="trust-row"><div><strong>4</strong><span>curated courses</span></div><div><strong>100%</strong><span>progress visibility</span></div><div><strong>1</strong><span>focused dashboard</span></div></div>
      </div>
      <div className="hero-visual" aria-label="Learning progress illustration">
        <div className="visual-grid"/>
        <div className="floating-label label-one">SPRING MVC</div><div className="floating-label label-two">REACT</div>
        <div className="hero-card">
          <div className="hero-card-top"><span className="course-icon"><Icon name="book" size={25}/></span><span className="status-pill">In progress</span></div>
          <p>Current path</p><h3>Full-Stack<br/>Project Lab</h3>
          <div className="large-progress"><div><strong>68%</strong><span>8 of 12 lessons</span></div><div className="progress-track"><span style={{width: '68%'}}/></div></div>
          <div className="next-lesson"><span><Icon name="play" size={16}/></span><div><small>UP NEXT</small><strong>Connecting the API</strong></div><Icon name="arrow" size={18}/></div>
        </div>
        <div className="score-bubble"><Icon name="trophy"/><div><strong>Quiz streak</strong><span>3 completed</span></div></div>
      </div>
    </section>

    <section className="why section-pad">
      <div className="section-heading"><p className="kicker"><span/> Designed for progress</p><h2>Everything you need.<br/><em>Nothing you don't.</em></h2></div>
      <div className="feature-grid">
        <article><span className="feature-number">01</span><Icon name="book" size={30}/><h3>Clear learning paths</h3><p>Courses are organised into focused lessons with the context you need to keep moving.</p></article>
        <article><span className="feature-number">02</span><Icon name="check" size={30}/><h3>Active recall</h3><p>Short interactive quizzes turn reading into understanding and show where to revisit.</p></article>
        <article><span className="feature-number">03</span><Icon name="chart" size={30}/><h3>Visible momentum</h3><p>Your dashboard keeps enrollment, progress, and best quiz scores in one calm view.</p></article>
      </div>
    </section>

    <section className="team-section section-pad" id="team">
      <div className="section-heading split-heading"><div><p className="kicker light"><span/> The project team</p><h2>Built together.<br/><em>Learned together.</em></h2></div><p>This case study was designed and developed by a three-member student team as a practical full-stack learning project.</p></div>
      <div className="team-grid">{teamMembers.map((member, index) => <article key={member.rollNumber}><span className="member-index">0{index + 1}</span><div className="avatar">{member.initials}</div><h3>{member.name}</h3><p>{member.rollNumber}</p><span className="member-role">Student developer</span></article>)}</div>
    </section>

    <section className="cta-section section-pad"><div><p className="kicker"><span/> Your next chapter</p><h2>Ready to make<br/><em>real progress?</em></h2></div><Link className="button button-dark" to="/register">Create your account <Icon name="arrow"/></Link></section>
  </>
}

