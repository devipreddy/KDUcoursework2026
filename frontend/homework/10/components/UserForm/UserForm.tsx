import React, { useState } from "react";
import { useAddUserMutation } from "../../services/users/usersApi";
import type { CreateUserPayload } from "../../type/User";
import styles from "./UserForm.module.scss";

const initialFormState: CreateUserPayload = {
  firstName: "",
  lastName: "",
  age: 0,
  email: "",
};

const UserForm: React.FC = () => {
  const [formData, setFormData] = useState<CreateUserPayload>(initialFormState);

  const [addUser, { isLoading , isSuccess}] = useAddUserMutation();
  const handleSubmit = async (e: React.SubmitEvent<HTMLFormElement>) => {

    e.preventDefault();

    try {
      await addUser(formData).unwrap(); // unwrap throws if error
      setFormData(initialFormState); // clear form
    } catch (error) {
      console.error("Failed to add user", error);
    }
  };
  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement>) => { const { name, value } = e.target;

    setFormData((prev) => ({...prev, [name]: name === "age" ? Number(value) : value,}));
  };


  return (
    <form className={styles.userForm} onSubmit={handleSubmit}>
      <h1 className={styles.userFormHeading}>Add User Form</h1>
      <div className={styles.formRow}>
        <input name="firstName" placeholder="First Name" value={formData.firstName} onChange={handleChange}/>

        <input name="lastName" placeholder="Last Name" value={formData.lastName} onChange={handleChange}/>

        <input name="age" type="number" placeholder="Age" value={formData.age} onChange={handleChange}/>

        <input name="email" type="email" placeholder="Email" value={formData.email} onChange={handleChange}/>

        <button type="submit" disabled={isLoading}>{isLoading ? "Adding..." : "Submit"}</button>

      </div>
      {isSuccess && <p className={styles.successMessage}>User added successfully!</p>}
    </form>
  );
};

export default UserForm;
