import React, { useState } from "react";
import "./styles.css";
import { chapters, pages } from "./data";

const GrandChild = ({ page, selectedPage, onClickPage }) => {
  const { id, text } = page;
  const selected = selectedPage === id;

  return (
    <div
      onClick={onClickPage(id)}
      style={{
        border: `1px solid ${selected ? "red" : "gray"}`,
        margin: 5,
        cursor: "pointer"
      }}
    >
      {selected ? text : ""} ({id})
    </div>
  );
};

const Child = ({
  chapter,
  selectedChapter,
  selectedPage,
  onClickChapter,
  onClickPage
}) => {
  const { title, id } = chapter;
  const selected = selectedChapter === chapter.id;

  const actualPages = pages.filter((item) =>
    chapter.pages.find((chapterPage) => chapterPage === item.id)
  );

  return (
    <div
      onClick={onClickChapter(id)}
      style={{
        border: `2px solid ${selected ? "gold" : "gray"}`,
        margin: 10,
        cursor: "pointer"
      }}
    >
      <h2>{title}</h2>

      {selected
        ? actualPages.map((page) => (
            <GrandChild
              key={page.id}
              page={page}
              selectedPage={selectedPage}
              onClickPage={onClickPage}
            />
          ))
        : null}
    </div>
  );
};

export default function Parent() {
  const [selectedChapter, setSelectedChapter] = useState();
  const [selectedPage, setSelectedPage] = useState();

  const onClickChapter = (id) => () => {
    setSelectedChapter(id);
  };

  const onClickPage = (id) => () => {
    setSelectedPage(id);
  };

  return (
    <div className="App">
      Click in the chapters and pages to reveal them:
      {chapters.map((chapter) => (
        <Child
          key={chapter.id}
          chapter={chapter}
          selectedChapter={selectedChapter}
          selectedPage={selectedPage}
          onClickChapter={onClickChapter}
          onClickPage={onClickPage}
        />
      ))}
    </div>
  );
}