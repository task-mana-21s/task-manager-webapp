import React, { useEffect, useState } from "react";
import "./LoginPage.css";
import { userData } from "../../types";
import { Form } from "reactstrap";
import { useNavigate } from "react-router-dom";
import { register } from "../../RequestHandler";
import axios from "axios";
import { useLocalState } from "../../useLocalStorage";

function LoginPage() {
  const navigate = useNavigate();
  const navToRegister = () => {
    navigate("/register");
  };

  //make request to localhost with sending username and password to it to login
  const login = async (username: string, password: string) => {
    console.log("logging in");
    let res = await axios.post("http://localhost:8080/api/login", {
      username,
      password,
    });
    console.log("--------response--------");
    console.log(res.data);
    return res.data;
  };

  //React Hook to set a state and change based on the current status
  let [user, setUser] = useState<userData | null>(null);
  let [status, setStatus] = useState("idle");
  let [username, setUsername] = useState("");
  let [password, setPassword] = useState("");


  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    console.log("submitting");
    e.preventDefault();
    setStatus("pending");
    try {
        let user = await login(username, password);
        setUser(user);
      // else {
      //   let user = await register(username, password, email);
      //   setUser(user);
      // }
      setStatus("success");

    } catch (e: any) {
      console.log("error", e);
      setStatus("error");
    }
  };

  if (status === "pending") {
    return <div className="text-center">Loading...</div>;
  }

  if (status === "error") {
    return <div className="text-center">Error:</div>;
  }
  if (status === "success") {
    console.log("-------success data---------");
    console.log(user);
    localStorage.setItem("user", JSON.stringify(user));
    navigate("/tasks");
  }

  const submitButton = () => {
    return (
      <div className="d-grid gap-2 mt-3">
        <button
          type="submit"
          className="btn btn-primary"
          onClick={() => handleSubmit}
        >
          Submit
        </button>
      </div>
    );
  };

  //input for username
  const usernameInput = () => {
    return (
      <div className="form-group mt-3">
        <label>Username</label>
        <input
          type="text"
          className="form-control mt-1"
          placeholder="Enter Username"
          onChange={(e) => setUsername(e.target.value)}
        />
      </div>
    );
  };

  //input for password
  const passwordInput = () => {
    return (
      <div className="form-group mt-3">
        <label>Password</label>
        <input
          type="password"
          className="form-control mt-1"
          placeholder="Enter Password"
          onChange={(e) => setPassword(e.target.value)}
        />
      </div>
    );
  };

 
  //button to call handleSubmit function in my form

    return (
      <div className="Auth-form-container">
        <Form className="Auth-form" onSubmit={handleSubmit}>
          <div className="Auth-form-content">
            <h3 className="Auth-form-title">Sign In</h3>
            <div className="text-center">
              Not registered yet?{" "}
              <span className="link-primary" onClick={navToRegister}>
                Sign Up
              </span>
            </div>
            {usernameInput()}
            {passwordInput()}
            {submitButton()}
          </div>
        </Form>
      </div>
    );
}

export default LoginPage;