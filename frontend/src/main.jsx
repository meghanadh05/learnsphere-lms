import React from 'react'
import ReactDOM from 'react-dom/client'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import { AuthProvider } from './context/AuthContext'
import Layout from './components/Layout'
import HomePage from './pages/HomePage'
import CoursesPage from './pages/CoursesPage'
import CourseDetailPage from './pages/CourseDetailPage'
import DashboardPage from './pages/DashboardPage'
import AuthPage from './pages/AuthPage'
import './styles.css'

ReactDOM.createRoot(document.getElementById('root')).render(<React.StrictMode><BrowserRouter><AuthProvider><Layout><Routes><Route path="/" element={<HomePage/>}/><Route path="/courses" element={<CoursesPage/>}/><Route path="/courses/:id" element={<CourseDetailPage/>}/><Route path="/dashboard" element={<DashboardPage/>}/><Route path="/login" element={<AuthPage mode="login"/>}/><Route path="/register" element={<AuthPage mode="register"/>}/><Route path="*" element={<HomePage/>}/></Routes></Layout></AuthProvider></BrowserRouter></React.StrictMode>)
