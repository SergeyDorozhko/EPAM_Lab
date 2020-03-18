import React, { Component } from "react";
import { renderToStaticMarkup } from "react-dom/server";
import { withLocalize } from "react-localize-redux";
import globalTranslations from "./../../translations/global";

class Localizer extends Component {
  constructor(props) {
    super(props);

    this.props.initialize({
      languages: [
        { name: "EN", code: "en" },
        { name: "FR", code: "fr" },
        { name: "RU", code: "ru" }
      ],
      translation: globalTranslations,
      options: { renderToStaticMarkup }
    });
  }

  render() {
    return <div></div>
  }
}

export default withLocalize(Localizer);