import React, { useEffect } from 'react';
import {
  faChartBar,
  faChartPie,
  faEye,
  faGamepad,
  faPlay,
  faSearch,
  faTable, faUser,
  faUsers
} from '@fortawesome/free-solid-svg-icons';
import SidebarMenu from './SidebarMenu';

function SideBar() {
  const menuSelected = (path) => {
    const aMenuArr = document.getElementsByClassName('t-menu-a');
    for(let i = 0 ; i < aMenuArr.length ; ++i) {
      const menuHref = aMenuArr.item(i).getAttribute('href');
      if(path === menuHref)
        aMenuArr.item(i).children[0].classList.add('t-menu-selected');
    }
  }
  useEffect(() => {
    menuSelected(window.location.pathname);
  })

  /* Menu */
  const game = {
    menu: {
      title: '게임',
      group: 'Game',
      hasSubMenu: true,
      icon: faGamepad,
    },
    subMenu: {
      game: {
        key: 'game',
        title: '게임 시작',
        group: 'Game',
        icon: faPlay,
        href: '/game/play'
      },
      rank: {
        key: 'rank',
        title: '순위',
        group: 'Game',
        icon: faTable,
        href: '/game/rank'
      }
    }
  }

  const lotto = {
    menu: {
      title: '로또',
      group: 'Lotto',
      hasSubMenu: true,
      icon: faSearch,
    },
    subMenu: {
      result: {
        key: 'result',
        title: '결과조회',
        group: 'Lotto',
        icon: faChartBar,
        href: 'lotto/result'
      }
    }
  }
  /* news 관련 메뉴 상수 */
  const news = {
    menu: {
      title: '뉴스',
      group: 'News',
      hasSubMenu: true,
      icon: faEye,
    },
    subMenu: {
      yonhap: {
        key: 'yonhap',
        title: '연합뉴스',
        group: 'News',
        icon: faChartPie,
        href: '/news/yonhap'
      }
    }
  }

  const user = {
    menu: {
      title: '사용자',
      group: 'User',
      hasSubMenu: true,
      icon: faUsers,
    },
    subMenu: {
      management: {
        key: 'user-management',
        title: '사용자 관리',
        group: 'User',
        icon: faUser,
        href: '/user/management'
      },
    }
  }

  useEffect(() => {
  })

  return (
      <>
      {/* flex를 위한 div */}
      <div>
        <div className="t-sidebar" id="sidebar-wrapper">
          <div className="t-sidebar-logo">Developer H.</div>
          <div className="p-2"></div>
          <div className="list-group list-group-flush">
            <SidebarMenu menuObject={game} />
            <SidebarMenu menuObject={lotto} />
            <SidebarMenu menuObject={news} />
            <SidebarMenu menuObject={user} />
          </div>
        </div>
      </div>
      </>
  );
}

export default SideBar;