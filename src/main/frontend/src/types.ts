export type userData ={
    userId?: number;
    username: string;
    password: string;
    email?: string;
  }

  export type taskData ={
    id:number,
    name: String,
    description: String,
    user: userData,
    status:statusData,
  }

export type statusData = {
    id?: number;
    status: string;
}