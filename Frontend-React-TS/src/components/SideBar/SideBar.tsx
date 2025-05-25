import { Sidebar, Menu, MenuItem, SubMenu } from "react-pro-sidebar";
import { NavLink } from "react-router-dom";

interface ISidebarComponentProps {
  children: React.ReactNode;
}

const SideBar = ({ children }: ISidebarComponentProps) => {
  return (
    <div style={{ display: "flex", height: "100vh" }}>
      <Sidebar style={{ fontSize: 13 }} width="18vw">
        <Menu
          menuItemStyles={{
            button: ({ active }) => ({
              backgroundColor: active ? "#EBF5FB" : undefined,
              color: active ? "#EBF5FB" : "black",
              borderRadius: "8px",
              fontWeight: active ? "bold" : "normal",
              "&:hover": {
                color: "#154360",
                backgroundColor: "#EBF5FB",
                fontWeight: "bold",
              },
            }),
          }}
        >
          <MenuItem disabled className="title">
            <b>Super DeUno</b>
          </MenuItem>

          <SubMenu label="Product">
            <MenuItem component={<NavLink to="/Product/" />}>Products</MenuItem>
            <MenuItem component={<NavLink to="/Product/Movement" />}>
              Movement Product
            </MenuItem>
          </SubMenu>
        </Menu>
      </Sidebar>

      <div style={{ height: "100vh", width: "82vw", padding: "20px" }}>
        {children}
      </div>
    </div>
  );
};

export default SideBar;
