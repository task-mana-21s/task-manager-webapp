import { Box, Button, Card, CardActions, CardContent, TextField, Typography } from "@mui/material";
import { toast, ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';


import React, { useEffect, useState } from "react";
import { userData } from "../../types";

function AddTask({editName, editDescription, setEditName, createTaskRequest, setAddTaskState, setEditDescription}:any) {

    let [user, setUser] = useState<userData | null>(null);
    useEffect(() => {

        let localuser = localStorage.getItem("user");
        let userObj;
        if(localuser){
          userObj = JSON.parse(localuser!);
        }
        if(userObj && userObj.username && userObj.password && userObj.username!== "" && userObj.password !== ""){
          setUser(userObj);

        }
    }, []);
    const handleCreateTask = () => {
        createTaskRequest(null);
        toast.success("Task created successfully!");
    };

    const handleCancel = () => {
        setAddTaskState(-1);
        toast.info("Task creation canceled.");
    };

    return (
        <Box   sx={{ minWidth: 250 }}>
          <Card style={{ backgroundColor: "rgba(192, 245, 190 )", width: "50%", margin:"auto"}} variant="outlined">
          <React.Fragment>
            <CardContent>
              <Typography sx={{ fontSize: 14 }} color="text.secondary" gutterBottom>
                {/* {user? user.username : ""} */}
              </Typography>
                <Typography sx={{ fontSize: 14 }} color="text.secondary" gutterBottom>
                    {/* {status? status.status : ""} */}
                </Typography>
              <TextField id="outlined-basic" label="Task name" variant="outlined" placeholder={"Task Name"}
            onChange={event => {
              const { value } = event.target;
              setEditName(value)}} style={{marginBottom:"10px"}}/>
              <TextField id="outlined-basic" label="Task description" variant="outlined" placeholder={"Task Description"}
            onChange={event => {
              const { value } = event.target;
              setEditDescription(value)}} />

              {/* <Typography variant="body2">
                {task.description}
              </Typography> */}
            </CardContent>

            <CardActions>
                <Button variant="contained" type="submit" onClick={handleCreateTask}>
                    Create Task

                </Button>
                <Button variant="contained" type="submit" color="error" onClick={handleCancel}>
                    Cancel

                </Button>
            </CardActions>
          </React.Fragment>
          </Card>
            <ToastContainer/>
        </Box> );
}

export default AddTask;

