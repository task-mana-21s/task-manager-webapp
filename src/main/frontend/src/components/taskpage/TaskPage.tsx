import axios from "axios";
import { taskData } from "../../types";
import { useEffect, useState } from "react";
import * as React from 'react';
import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Edit from "./Edit";
import AddIcon from '@mui/icons-material/Add';
import AddTask from "./AddTask";
import AssignUser from "./AssignUser";
import AssignStatus from "./AssingStatus";

function TaskPage() {
  const [listOfTasks, setListOfTasks] = useState([]);
  const [updateState, setUpdateState] = useState(-1);
  const [setAddTask, setAddTaskState] = useState(-1)
  const [editName, setEditName] = useState("");
  const [editDescription, setEditDescription] = useState("");
  const getTaskRequest = async () => {
    try {
      const { data: response } = await axios.get(
        "http://localhost:8080/api/tasks"
      );
      if (response._embedded === undefined && response._embedded?.taskList === undefined) {
        return;
      }
      setListOfTasks(response._embedded.taskList);
      return response;
    } catch (error) {
      console.log(error);
    }
  };

  const updateTaskRequest = async (task: any) => {
    const { id } = task;
    try {
      const { data: response } = await axios.put(
        "http://localhost:8080/api/tasks/" + id.toString(),
        {
          id: task.id,
          name: editName,
          description: editDescription,

        },
        { headers: { "Access-Control-Allow-Origin": "*" } },
      );
      setUpdateState(-1);
      setEditName("");
      setEditDescription("");
      getTaskRequest();

    } catch (error) {
      console.log(error);
    }
  };

  const createTaskRequest = async (task: any) => {
    try {
      const { data: response } = await axios.post(
        "http://localhost:8080/api/tasks",
        {
          name: editName,
          description: editDescription,
          // user:{
          //   username: "user",
          // }

        },
        { headers: { "Access-Control-Allow-Origin": "*" } },
      );
      setAddTaskState(-1);
      setEditName("");
      setEditDescription("");
      getTaskRequest();

    } catch (error) {
      console.log(error);
    }
  };

  const handleEdit = (id: number) => {
    setUpdateState(id);
    setAddTaskState(-1);
  }
  const deleteTaskRequest = async (id: number) => {

    try {
      const { data: response } = await axios.delete(
        "http://localhost:8080/api/tasks/" + id.toString(),
        { headers: { "Access-Control-Allow-Origin": "*" } }
      );
      setListOfTasks(listOfTasks.filter((task: taskData) => task.id !== id));
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    getTaskRequest();
  }, []);


  return (
    <>
    <div>
      {listOfTasks.map((task: taskData) => {
        return (
          updateState === task.id ? <Edit task={task} editName={editName} editDescription={editDescription} setEditName={setEditName} updateTaskRequest={updateTaskRequest} setUpdateState={setUpdateState} setEditDescription={setEditDescription} /> :
            <Box key={task.id} sx={{ minWidth: 250 }}>
              <Card style={{ backgroundColor: "rgba(192, 245, 190 )", width: "50%", margin: "auto" }} variant="outlined">  <React.Fragment>
                <CardContent>
                  <Typography sx={{ fontSize: 14 }} color="text.secondary" gutterBottom>
                  <AssignUser userOnTask={task?.user} taskId={task.id} getTaskRequest={getTaskRequest}/>
                  </Typography>
                  <Typography sx={{ fontSize: 14 }} color="text.secondary" gutterBottom>
                    <AssignStatus statusOnTask={task?.status} taskId={task.id} getTaskRequest={getTaskRequest}/>
                  </Typography>
                  <Typography variant="h5" component="div">
                    {task.name}
                  </Typography>
                  <Typography variant="body2">
                    {task.description}
                  </Typography>
                </CardContent>
                <CardActions>
                  <Button variant="contained" onClick={() => {
                    handleEdit(task.id);
                  }}>Edit</Button>
                  <Button variant="contained" color="error" onClick={() => {
                    deleteTaskRequest(task.id);
                  }
                  }>Delete</Button>
                </CardActions>
              </React.Fragment></Card>
            </Box>
        );
      })}
      {setAddTask === 1 && <AddTask editName={editName} editDescription={editDescription} setEditName={setEditName} createTaskRequest={createTaskRequest} setAddTaskState={setAddTaskState} setEditDescription={setEditDescription} />}
    </div>
      <div style={{
        position: "absolute",
        bottom: 0,
        right: 0,
        marginRight: "1%",
        marginBottom: "1%"
      }}>
        <Button variant="contained" color="success" onClick={() => { setAddTaskState(1); setUpdateState(-1) }} style={{
          height: "100px",
          width: "100px",
          borderRadius: "50%",
        }}><AddIcon sx={{
          height: "70%",
          width: "70%"
        }} /></Button>
      </div>
  </>);
}

export default TaskPage;


