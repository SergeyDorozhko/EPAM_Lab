import React from "react";
import { NavLink } from "react-router-dom";



const Pagination = (props) => {
    let pagesCount = props.totalNews / props.pageSize;

    let pages = []
    for (let i = 1; i <= pagesCount; i++) {
        pages.push(i)
    }
    return (
        <div>
            <ul className="pagination pagination-sm">
                <li className={"page-item" + (props.currentPage == 1 ? " disabled" : "")}>
                    <NavLink className="page-link "
                        to={"?page=" + (props.currentPage - 1)}
                        onClick={() => props.onClick(props.currentPage - 1)}>
                        Previous
                        </NavLink>
                </li>

                {pages.map(page =>
                    <li className={"page-item" + (props.currentPage == page ? " active" : '')}>
                        <NavLink className="page-link"
                            to={"?page=" + page}
                            onClick={() => props.onClick(page)}>
                            {page}
                        </NavLink>
                    </li>
                )}

                <li className={"page-item" + (props.currentPage >= (pagesCount-1) ? " disabled" : "")}>
                    <NavLink className="page-link"
                        to={"?page=" + (props.currentPage + 1)}
                        onClick={() => props.onClick(props.currentPage + 1)}>
                        Next
                        </NavLink>
                </li>

            </ul>
        </div>
    )
}

export default Pagination;