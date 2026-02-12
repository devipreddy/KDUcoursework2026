import React from 'react'
import { useGetUsersQuery } from '../../services/users/usersApi';
import { useNavigate } from 'react-router-dom';
import styles from "./UsersGrid.module.scss";


const UsersGrid: React.FC = () => {

  const {data, error, isLoading} = useGetUsersQuery();
  const navigate = useNavigate();

  if (isLoading) return <p>Loading...</p>;
  if (error) return <p>Error...</p>;
  //if (isFetching) return <p>Updating...</p>;
  //if (currentData) return <p>Current Data...</p>;



  return (
    <div className={styles.userGrid} >
        { data?.map((user) => (
            <div key={user.id} className={styles.userCard} onClick = {() => navigate(`/users/${user.id}`)} role="button" tabIndex={0} onKeyDown={(e) => e.key === "Enter" && navigate(`/users/${user.id}`)} >
                <img src={user.image} alt={`${user.firstName} ${user.lastName}`} className={styles.userImage} />
                <div className={styles.userInfo}>
                    <h3>{user.firstName} {user.lastName}</h3>
                    <p>Age: {user.age}</p>
                    <p>Email: {user.email}</p>
                    <p>Phone: {user.phone}</p>
                </div>
            </div>
        ))}

        <div className={styles.totalUserCount}>Total Users: {data?.length}</div>
    </div>
  )
}

export default UsersGrid;
