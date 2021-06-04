import React, { useContext } from 'react'
import { ThemeProvider } from 'styled-components'
import Dashboard from './containers/Dashboard'
import { lightTheme, darkTheme } from './styles/theme'
import { GlobalStyles } from './styles/global'
import { ThemeContext } from './context/themeContext'

import {createStore} from 'redux';
import {Provider} from 'react-redux';

import rootReducer from './redux/rootReducer';

const store = createStore(rootReducer);

const App = () => {

  const context = useContext(ThemeContext);
  const { theme } = context;

  return (
    <Provider store={store}>
      <ThemeProvider theme={theme === 'light' ? lightTheme : darkTheme}>
        <>
          <GlobalStyles />
          <Dashboard />
        </>
      </ThemeProvider>
    </Provider>
  )
}

export default App
