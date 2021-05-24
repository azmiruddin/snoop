import React from 'react';
import Paper from '@material-ui/core/Paper';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';

export default function DisabledTabs() {
  const [value, setValue] = React.useState(2);

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  return (
    <Paper variant="fullWidth">
      <Tabs
         value={value}
         onChange={handleChange}
         variant="fitContent"
         indicatorColor="primary"
         textColor="primary"
         aria-label="icon label tabs example"
         centered
      >
        <Tab label="Transaction" />
        <Tab label="Channel" disabled />
      </Tabs>
    </Paper>
  );
}