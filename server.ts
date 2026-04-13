import express from "express";
import { createServer as createViteServer } from "vite";
import fs from "fs/promises";
import path from "path";
import { fileURLToPath } from "url";

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

async function startServer() {
  const app = express();
  const PORT = 3000;

  app.use(express.json());

  // API Routes for File Handling
  const FILES_DIR = path.join(__dirname, "user_files");
  
  // Ensure user_files directory exists
  try {
    await fs.mkdir(FILES_DIR, { recursive: true });
  } catch (err) {
    console.error("Could not create files directory", err);
  }

  // List files
  app.get("/api/files", async (req, res) => {
    try {
      const files = await fs.readdir(FILES_DIR);
      res.json({ files });
    } catch (error) {
      res.status(500).json({ error: "Could not list files" });
    }
  });

  // Create/Write file
  app.post("/api/files", async (req, res) => {
    const { name, content } = req.body;
    if (!name) return res.status(400).json({ error: "File name is required" });
    
    try {
      const filePath = path.join(FILES_DIR, name);
      await fs.writeFile(filePath, content || "");
      res.json({ message: "File created/updated successfully" });
    } catch (error) {
      res.status(500).json({ error: "Could not write file" });
    }
  });

  // Read file
  app.get("/api/files/:name", async (req, res) => {
    try {
      const filePath = path.join(FILES_DIR, req.params.name);
      const content = await fs.readFile(filePath, "utf-8");
      res.json({ content });
    } catch (error) {
      res.status(404).json({ error: "File not found" });
    }
  });

  // Append to file
  app.post("/api/files/:name/append", async (req, res) => {
    const { content } = req.body;
    try {
      const filePath = path.join(FILES_DIR, req.params.name);
      await fs.appendFile(filePath, "\n" + content);
      res.json({ message: "Content appended" });
    } catch (error) {
      res.status(500).json({ error: "Could not append to file" });
    }
  });

  // Delete file
  app.delete("/api/files/:name", async (req, res) => {
    try {
      const filePath = path.join(FILES_DIR, req.params.name);
      await fs.unlink(filePath);
      res.json({ message: "File deleted" });
    } catch (error) {
      res.status(500).json({ error: "Could not delete file" });
    }
  });

  // Vite middleware for development
  if (process.env.NODE_ENV !== "production") {
    const vite = await createViteServer({
      server: { middlewareMode: true },
      appType: "spa",
    });
    app.use(vite.middlewares);
  } else {
    const distPath = path.join(process.cwd(), "dist");
    app.use(express.static(distPath));
    app.get("*", (req, res) => {
      res.sendFile(path.join(distPath, "index.html"));
    });
  }

  app.listen(PORT, "0.0.0.0", () => {
    console.log(`Server running on http://localhost:${PORT}`);
  });
}

startServer();
