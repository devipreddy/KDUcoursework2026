import { Suspense } from "react";
import AppRouter from "./routes/AppRouter";
import Loader from "./components/Loader/Loader";

function App() {
  return (
    <Suspense fallback={<Loader />}>
      <AppRouter />
    </Suspense>
  );
}

export default App;