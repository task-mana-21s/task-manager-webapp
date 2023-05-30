import { Box, Button, Card, CardActions, CardContent, TextField, Typography } from "@mui/material";
import { taskData } from "../../types";
import React from "react";

function Edit({task, editName, editDescription, setEditName, updateTaskRequest, setUpdateState, setEditDescription}:any) {
    return (  
        <Box  key={task.id} sx={{ minWidth: 250 }}>
          <Card style={{ backgroundColor: "rgba(192, 245, 190 )", width: "50%"}} variant="outlined">  
          <React.Fragment>
            <CardContent>
              <Typography sx={{ fontSize: 14 }} color="text.secondary" gutterBottom>
                {task.user?.username}
              </Typography>
              <TextField id="outlined-basic" label="Task name" variant="outlined" placeholder={task.name}
            onChange={event => {
              const { value } = event.target;
              setEditName(value)}} style={{marginBottom:"10px"}}/>
              <TextField id="outlined-basic" label="Task description" variant="outlined" placeholder={task.description}
            onChange={event => {
              const { value } = event.target;
              setEditDescription(value)}} />
      
              {/* <Typography variant="body2">
                {task.description}
              </Typography> */}
            </CardContent>
            <CardActions>
              <Button variant="contained" type="submit" onClick={()=>{updateTaskRequest(task)}}>Submit</Button>
              <Button variant="contained" type="submit" color="error" onClick={()=>{setUpdateState(-1)}}>Cancel</Button>
      
            </CardActions>
          </React.Fragment>
          </Card>
        </Box> );
}

export default Edit;

