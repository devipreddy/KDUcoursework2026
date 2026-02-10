import React from "react";
import type { ReactNode } from "react";
import ErrorState from "./ErrorState";

interface ErrorBoundaryProps {
  children: ReactNode;
}

interface ErrorBoundaryState {
  hasError: boolean;
  error: Error | null;
}

// Top-level error boundary to catch unexpected errors
export class ErrorBoundary extends React.Component<ErrorBoundaryProps, ErrorBoundaryState> {
  constructor(props: ErrorBoundaryProps) {
    super(props);
    this.state = { hasError: false, error: null };
  }

  static getDerivedStateFromError(error: Error): ErrorBoundaryState {
    return { hasError: true, error };
  }

  componentDidCatch(error: Error, errorInfo: React.ErrorInfo) {
    console.error("ErrorBoundary caught:", error, errorInfo);
  }

  handleRetry = () => {
    this.setState({ hasError: false, error: null });
  };

  render() {
    if (this.state.hasError) {
      return (
        <div style={{ padding: "20px", textAlign: "center" }}>
          <h1>An Unexpected Error Occurred</h1>
          <ErrorState message={this.state.error?.message ?? "Something went wrong"} />
          <div style={{ marginTop: 12 }}>
            <button onClick={this.handleRetry}>Retry</button>
          </div>
        </div>
      );
    }

    return this.props.children;
  }
}
