import React from 'react';
import './App.css';
import InstructorApp from './component/InstructorApp';
import { LocalizeProvider } from "react-localize-redux";
import Localizer from './component/Locale/Localizer';

function App() {
  return (
    <LocalizeProvider>
      <Localizer/>
      <InstructorApp />
    </LocalizeProvider>

  );
}

export default App;
