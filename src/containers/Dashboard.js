import React from 'react'

import SideBar from '../components/Sidebar/Sidebar'
import SimpleTransaction from '../components/SimpleTransaction/SimpleTransaction'
import StateChannel from '../components/StateChannel/StateChannel'
// import Homepage from '../components/Homepage/Homepage'
import Nav from './Nav';

import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import UnderDevelopment from '../components/StateChannel/UnderDevelopment';

const routes = [
    {
      path: "/",
      exact: true,
      main: () => <StateChannel/>
    },
    {
      path: "/onchain",
      main: () => <SimpleTransaction/>
    },
    {
      path: "/systemconfig",
      main: () => <UnderDevelopment />
    }
  ];

const Dashboard = () => {
    return (
        <React.Fragment>
             <Router basename="/statechannel">
                <SideBar />
                <Nav />
                <Route path="/"/>
                <Route path="/onchain" />
                <Route path="/systemconfig" />

                <Switch>
                    {routes.map((route, index) => (
                    // Render more <Route>s with the same paths as
                    // above, but different components this time.
                    <Route
                        key={index}
                        path={route.path}
                        exact={route.exact}
                        children={<route.main />}
                    />
                    ))}
                </Switch>
            </Router>
            
        </React.Fragment>
       
    )
}

export default Dashboard
