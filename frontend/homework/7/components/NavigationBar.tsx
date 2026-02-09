import { NavLink } from "react-router-dom";

const NavigationBar: React.FC = () => {
  return (
    <nav className="navigation-bar">
      <NavLink to="/" className={({ isActive }) => (isActive ? "active" : "")}>
        Home
      </NavLink>
    </nav>
  );
};

export default NavigationBar;
