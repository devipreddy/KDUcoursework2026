import { writeFile, mkdir } from "fs/promises";
import path from "path";
import { access } from "fs/promises";

export async function writeReport(filepath, content) {
  const fileDirectory = path.dirname(filepath);

  try {
    await access(fileDirectory);

  } catch {
    await mkdir(fileDirectory, { recursive: true });

  }
  await writeFile(filepath, content, "utf-8");

  console.log("Report saved to:", filepath);
}
