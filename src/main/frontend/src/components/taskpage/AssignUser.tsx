import * as React from "react";
import TextField from "@mui/material/TextField";
import Autocomplete from "@mui/material/Autocomplete";
import CircularProgress from "@mui/material/CircularProgress";
import { userData } from "../../types";
import axios from "axios";

const getAllUserRequest = async () => {
  try {
    const { data: response } = await axios.get(
      "http://localhost:8080/api/users",
      { headers: { "Access-Control-Allow-Origin": "*" } }
    );
    return response;
  } catch (error) {
    console.log(error);
  }
};

const assignUserToTaskRequest = async (userId:number, id:number) => {
  try {
    console.log("REQUEST SENT ASSIGNUSER ")
    const { data: response } = await axios.post(
      "http://localhost:8080/api/tasks/"+ id.toString()+"/user/"+userId.toString(),
      { headers: { "Access-Control-Allow-Origin": "*" } }
    );
    return response;
  } catch (error) {
    console.log(error);
  }
};

const getAllUsers = async (): Promise<userData[]> => {
  try {
    // console.log("get all users");
    let user = await getAllUserRequest();
    user = user._embedded.userList;
    // console.log("get all users ", user);
    user = user.map((user: userData) => {
      return {
        userId: user.userId,
        username: user.username,
        email: user.email,
        password: user.password,
      };
    });
    return user;
  } catch (e: any) {
    console.log("error", e);
    return [];
  }
};

export default function AssignUser({userOnTask, taskId, getTaskRequest}: {userOnTask:userData, taskId:number, getTaskRequest:any}) {
  const [open, setOpen] = React.useState(false);
  const [usersOptions, setUsersOptions] = React.useState<userData[]>([]);
  const [selectedUser, setSelectedUser] = React.useState<userData | null>(null);
  const loading = open && usersOptions.length === 0;


  React.useEffect(() => {
    let active = true;
    setSelectedUser(userOnTask);
    if (!loading) {
      return undefined;
    }
    (async () => {
      if (active) {
        let users: userData[] = await getAllUsers();
        if (users.length !== 0) {
          setUsersOptions(users);
        }
      }
    })();
    return () => {
      active = false;
    };
  }, [loading]);




  React.useEffect(() => {
    if (!open) {
      setUsersOptions([]);
    }
  }, [open]);


  return (
    <Autocomplete
      id="async-userassign"
      sx={{ width: 300 }}
      open={open}
      onOpen={() => {
        setOpen(true);
      }}
      onClose={() => {
        setOpen(false);
      }}
      isOptionEqualToValue={(usersOptions, value) =>
        usersOptions.username === value.username
      }
      clearOnEscape
      value={selectedUser}
      onChange={(event: any, newValue: userData | null) => {
        if (newValue !== null) {
          assignUserToTaskRequest(newValue.userId!, taskId);
          setSelectedUser(newValue)
          getTaskRequest();
        }else{
            assignUserToTaskRequest(-1, taskId);
            setSelectedUser(null); 
            getTaskRequest();
        }
      }}
      getOptionLabel={(usersOptions) => usersOptions.username}
      options={usersOptions}
      loading={loading}
      renderInput={(params) => (
        <TextField
          {...params}
          label="Assign User"
          InputProps={{
            ...params.InputProps,
            endAdornment: (
              <React.Fragment>
                {loading ? (
                  <CircularProgress color="inherit" size={20} />
                ) : null}
                {params.InputProps.endAdornment}
              </React.Fragment>
            ),
          }}
        />
      )}
    />
  );
}
