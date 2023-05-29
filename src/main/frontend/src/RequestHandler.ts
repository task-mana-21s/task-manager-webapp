import axios from "axios";




  //make request to localhost with sending username, password and email to it to register
 export const register = async (username: string, password: string, email: string) => {
    console.log("registering");
    let res = await axios.post("http://localhost:8080/api/register", {
      username,
      password,
      email,
    });
    return res.data;
  };

//   //make request to localhost with sending username, password and email to it to register
//  export const getTasks = async () => {
//     console.log("getting tasks");
//     let res = await axios.get("http://localhost:8080/api/tasks");
//     return res.data;
//   };