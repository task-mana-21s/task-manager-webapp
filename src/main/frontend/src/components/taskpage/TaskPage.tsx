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
import { TextField } from "@mui/material";


function TaskPage() {
  const [fetchData, setFetchData] = useState([]);
  const getTaskRequest = async () => {
    try {
      const { data: response } = await axios.get(
        "http://localhost:8080/api/tasks"
      );
      if(response._embedded === undefined && response._embedded?.taskList===undefined ){
        return;
      }
      setFetchData(response._embedded.taskList);
      return response;
    } catch (error) {
      console.log(error);
    }
  };

  const updateTaskRequest = async (task:any) => {

    const {id} = task;
    try {
      const { data: response } = await axios.put(
        "http://localhost:8080/api/tasks/"+id.toString(),
        {
          id:task.id,
          name: editName,
          description: task.description,

      },
        {headers: {"Access-Control-Allow-Origin": "*"}},
      );
      setUpdateState(-1)
      
    } catch (error) {
      console.log(error);
    }
  };
const handleEdit= (id:number) =>{
  setUpdateState(id)
}
const handleInput=(e:any)=>{
  console.log("HANDLE ASDAOSD;LSAD")
  console.log(e.target.value);
  console.log(editName);
  setEditName(e.target.value);
}
  const deleteTaskRequest = async (id:number) => {

    try {
      const { data: response } = await axios.delete(
        "http://localhost:8080/api/tasks/"+id.toString(),
        {headers: {"Access-Control-Allow-Origin": "*"}}
      );
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    getTaskRequest();
  }, []);

  const [updateState, setUpdateState] = useState(-1)
  const [editName, setEditName] = useState("");
  const Edit= ({task}:any) => {
    return (  
    <Box  sx={{ minWidth: 250 }}>
      <Card style={{ backgroundColor: "rgba(192, 245, 190 )", width: "50%"}} variant="outlined">  <React.Fragment>
        <CardContent>
          <Typography sx={{ fontSize: 14 }} color="text.secondary" gutterBottom>
            {task.user?.username}
          </Typography>
          <TextField id="outlined-basic" label="Task name" variant="outlined" value={editName} defaultValue={task.name} onChange={handleInput} style={{margin:"10px"}}/>
          <TextField id="outlined-basic" label="Description" variant="outlined" defaultValue={task.description} style={{margin:"10px"}}/>
            
  
          <Typography variant="body2">
            {task.description}
          </Typography>
        </CardContent>
        <CardActions>
          <Button variant="contained" type="submit" onClick={()=>{updateTaskRequest(task)}}>Submit</Button>
          <Button variant="contained" type="submit" color="error" onClick={()=>{setUpdateState(-1)}}>Cancel</Button>
  
        </CardActions>
      </React.Fragment></Card>
    </Box> );
  }  
  if(fetchData.length<1){
    return (<></>)
  }else{
    return (
      <div>
      {fetchData.map((task: taskData) => {
        return (
          updateState===task.id ? <Edit task={task}/> :
          <Box key={task.id} sx={{ minWidth: 250 }}>
            <Card style={{ backgroundColor: "rgba(192, 245, 190 )", width: "50%" }} variant="outlined">  <React.Fragment>
              <CardContent>
                <Typography sx={{ fontSize: 14 }} color="text.secondary" gutterBottom>
                  {task.user?.username}
                </Typography>
                <Typography variant="h5" component="div">
                  {task.name}
                </Typography>
                <Typography variant="body2">
                  {task.description}
                </Typography>
              </CardContent>
              <CardActions>
                <Button variant="contained"  onClick={()=>{
                  handleEdit(task.id);
                }}>Edit</Button>
                <Button variant="contained" onClick={()=>{
                  deleteTaskRequest(task.id);
                }
              }>Delete</Button>
              </CardActions>
            </React.Fragment></Card>
          </Box>
        );
        
      })}</div>);
    }
    
}

export default TaskPage;



