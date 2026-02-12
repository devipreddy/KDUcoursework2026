import React from 'react'
import UserForm from '../components/UserForm/UserForm';
import UsersGrid from '../components/UsersGrid/UsersGrid';
import styles from "./Home.module.scss";

const Home: React.FC = () => {
  return (
    <div className={styles.homePage}>
        <UserForm />
        <UsersGrid />
    </div>
  )
}

export default Home;
