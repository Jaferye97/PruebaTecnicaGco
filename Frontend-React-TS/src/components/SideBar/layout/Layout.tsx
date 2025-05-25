import SideBar from "../SideBar";
import { Outlet } from "react-router-dom";

const Layout = () => {
  return (
    <SideBar>
      <Outlet />
    </SideBar>
  );
};

export default Layout;
