import React from "react";
import { useParams, useNavigate } from "react-router-dom";
import { useGetUserByIdQuery } from "../../services/users/usersApi";
import styles from "./UserDetails.module.scss";
const UserDetails: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  const userId = id ? Number(id) : NaN;

  const { data: user, isLoading, isError, isSuccess, isFetching } = useGetUserByIdQuery(userId, {skip: !id || Number.isNaN(userId),});
  const isFromCache = isSuccess;
  const isFreshFetch = isLoading;

  // Invalid ID Case 
  if (!id || Number.isNaN(userId)) {
    return (
      <div className={styles.userDetails}>

        {isLoading && (<div className={styles.cacheBadge}> Using cached data </div>)}

        {isLoading && ( <div className={`${styles.cacheBadge} ${styles.fresh}`}> Fetching fresh data...</div>)}
        <p>Invalid user ID.</p>
        <button onClick={() => navigate("/")} className={styles.backButton}>Back to Home</button>
      </div>
    );
  }

  // Loading State
  if (isLoading) {
            {isFetching && ( <div className={`${styles.cacheBadge} ${styles.fresh}`}> Fetching fresh data...</div>)}
    return <p>Loading user details...</p>;
  }

  // Error State
  if (isError) {
    return (
      <div>
        <p>Failed to load user details.</p>
        <button onClick={() => navigate("/")}>Back to Home</button>
      </div>
    );
  }

  // Not Found Case
  if (!user) {
    return (
      <div>
        <p>User not found.</p>
        <button onClick={() => navigate("/")}>Back to Home</button>
      </div>
    );
  }

  return (
    <div className={styles.userDetails}>

        <div className={styles.headerRow}>
            <button onClick={() => navigate("/")} className={styles.backButton}> Back to Directory</button>
        </div>
        {/* First Load */}
        {isLoading && (
          <div className={`${styles.cacheBadge} ${styles.fresh}`}>
            Fetching fresh data...
          </div>
        )}

        {/* Background Refetch */}
        {!isLoading && isFetching && (
          <div className={styles.cacheBadge}>
            Using cached data (Refreshing...)
          </div>
        )}
    {!user && !isLoading ? (
      <p>User not found.</p>
    ) : ( user && (
      <div className={styles.userCard}>
        <img src={user.image} alt={`${user.firstName} ${user.lastName}`}  className={styles.userImage} />
        <div className={styles.userContent}>
            <h2>
            {user.firstName} {user.lastName}
            </h2>
            <div className={styles.infoGrid}>
                <div className={styles.infoItem}>
                    <strong>Email: {user.email} </strong> 
                </div>
                <div className={styles.infoItem}>
                    <strong>Phone: {user.phone}</strong> 
                </div>
                <div className={styles.infoItem}>
                    <strong>Age: {user.age} </strong> 
                </div>
                <div className={styles.infoItem}>
                    <strong>ID:{user.id}</strong> 
                </div>
            </div>
        </div>
      </div>
    ))}
    </div>
  );
};

export default UserDetails;
