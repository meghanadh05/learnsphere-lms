import { createContext, useContext, useEffect, useState } from 'react'
import { api, tokenStore } from '../services/api'

const AuthContext = createContext(null)

export function AuthProvider({ children }) {
  const [user, setUser] = useState(null)
  const [loading, setLoading] = useState(Boolean(tokenStore.get()))

  useEffect(() => {
    if (!tokenStore.get()) return
    api('/auth/me').then(setUser).catch(() => tokenStore.clear()).finally(() => setLoading(false))
  }, [])

  async function authenticate(mode, values) {
    const result = await api(`/auth/${mode}`, { method: 'POST', body: JSON.stringify(values) })
    tokenStore.set(result.token)
    setUser(result.user)
    return result.user
  }

  function logout() { tokenStore.clear(); setUser(null) }

  return <AuthContext.Provider value={{ user, loading, login: (v) => authenticate('login', v), register: (v) => authenticate('register', v), logout }}>{children}</AuthContext.Provider>
}

export const useAuth = () => useContext(AuthContext)

