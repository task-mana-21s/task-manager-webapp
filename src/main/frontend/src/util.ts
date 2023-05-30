export const getUserData = () =>
  new Promise((resolve) =>
    setTimeout(() => {
      const user = window.localStorage.getItem("user");
      resolve(user);
    }, 3000)
  );

  export const logout = () =>
  new Promise((resolve) =>
    setTimeout(() => {
      window.localStorage.removeItem("user");
      resolve(true);
    }, 3000)
  );