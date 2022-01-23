import React, {useEffect} from 'react';
import {useHistory} from 'react-router-dom';
import {checkLogin, removeToken} from 'common/AuthUtil';
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome';
import {faSignOutAlt} from '@fortawesome/free-solid-svg-icons';
import './Header.css';

function Header() {
  const toggle = (e) => {
    e.preventDefault();
    document.getElementById("wrapper").classList.toggle("toggled");
    window.setTimeout(() => window.dispatchEvent(new Event('resize')), 251);
  }

  const history = useHistory();

  useEffect(() => {
    // if(!checkLogin())
    //     history.push('/login');

  })

  const handleLogout = (e) => {
    e.preventDefault();
    removeToken();
    history.push('/login');
  }

  return (
    <>
      <nav className="t-navbar">
        <button className="btn btn-secondary t-btn-toggle" onClick={toggle} id="menu-toggle">메뉴 접기/펴기</button>

        <div className="t-navbar-user t-menu-selected t-text" id="header-user-info">
          {/* <div className="t-username">{ getAuthInfo().name }</div> */}
          <div className="t-logout">
            <button className="btn btn-secondary t-btn-toggle" onClick={handleLogout}>
              <FontAwesomeIcon id="logout-icon" size="2x" icon={faSignOutAlt}></FontAwesomeIcon>
            </button>
          </div>
        </div>
      </nav>
    </>
  );
}

export default Header;