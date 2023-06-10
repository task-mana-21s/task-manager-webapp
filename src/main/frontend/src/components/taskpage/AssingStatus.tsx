import * as React from "react";
import TextField from "@mui/material/TextField";
import Autocomplete from "@mui/material/Autocomplete";
import CircularProgress from "@mui/material/CircularProgress";
import {statusData} from "../../types";
import axios from "axios";

const getAllStatusRequest = async () => {
  try {
    const { data: response } = await axios.get(
      "http://localhost:8080/api/status",
      { headers: { "Access-Control-Allow-Origin": "*" } }
    );
    return response;
  } catch (error) {
    console.log(error);
  }
};

const assignStatusToTaskRequest = async (statusId:number, id:number) => {
  try {
    console.log("REQUEST SENT ASSIGNSTATUS ")
    const { data: response } = await axios.post(
      "http://localhost:8080/api/tasks/"+ id.toString()+"/status/"+statusId.toString(),
      { headers: { "Access-Control-Allow-Origin": "*" } }
    );
    return response;
  } catch (error) {
    console.log(error);
  }
};

const getAllStatus = async (): Promise<statusData[]> => {
  try {
    // console.log("get all status");
    let status = await getAllStatusRequest();
    status = status._embedded.statusList;
    // console.log("get all status ", status);
    status = status.map((status: statusData) => {
      return {
        status_id: status.status_id,
        status: status.status,
      };
    });
    return status;
  } catch (e: any) {
    console.log("error", e);
    return [];
  }
};

export default function AssignStatus({statusOnTask, taskId, getTaskRequest}: {statusOnTask:statusData, taskId:number, getTaskRequest:any}) {
  const [open, setOpen] = React.useState(false);
  const [statusOptions, setStatusOptions] = React.useState<statusData[]>([]);
  const [selectedStatus, setSelectedStatus] = React.useState<statusData | null>(null);
  const loading = open && statusOptions.length === 0;


  React.useEffect(() => {
    let active = true;
    setSelectedStatus(statusOnTask);
    if (!loading) {
      return undefined;
    }
    (async () => {
      if (active) {
        let status: statusData[] = await getAllStatus();
        if (status.length !== 0) {
          setStatusOptions(status);
        }
      }
    })();
    return () => {
      active = false;
    };
  }, [loading]);




  React.useEffect(() => {
    if (!open) {
      setStatusOptions([]);
    }
  }, [open]);


  return (
    <Autocomplete
      id="async-statusassign"
      sx={{ width: 300 }}
      open={open}
      onOpen={() => {
        setOpen(true);
      }}
      onClose={() => {
        setOpen(false);
      }}
      isOptionEqualToValue={(statusOptions, value) =>
        statusOptions.status === value.status
      }
      clearOnEscape
      value={selectedStatus}
      onChange={(event: any, newValue: statusData | null) => {
        if (newValue !== null) {
          assignStatusToTaskRequest(newValue.status_id!, taskId);
          setSelectedStatus(newValue)
          getTaskRequest();
        }else{
            assignStatusToTaskRequest(-1, taskId);
            setSelectedStatus(null);
            getTaskRequest();
        }
      }}
      getOptionLabel={(statusOptions) => statusOptions.status}
      options={statusOptions}
      loading={loading}
      renderInput={(params) => (
        <TextField
          {...params}
          label="Assign Status"
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
