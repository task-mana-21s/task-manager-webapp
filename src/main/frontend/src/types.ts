export type userData ={
    user_id?: number;
    username: string;
    password: string;
    email?: string;
    role?: string;
  }

  export type taskData ={
    id:number,
    name: String,
    description: String,
    user: userData,
    status:statusData,
  }

export type statusData = {
    status_id?: number;
    status: string;
}