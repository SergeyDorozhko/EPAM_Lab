import React from "react";
import { withLocalize } from "react-localize-redux";

const LanguageToggle = ({ languages, activeLanguage, setActiveLanguage }) => (
  <ul className="selector">
    {languages.map(lang => (
        <button type="button" className="btn" key={lang.code} onClick={() => setActiveLanguage(lang.code)}>
          {lang.name}
        </button>
    ))}
  </ul>
);

export default withLocalize(LanguageToggle);