import { useSelector } from "react-redux";
import type { RootState } from "../app/store";

export const useAppSelector = <TSelected>(selector: (state: RootState) => TSelected) => useSelector(selector);
