package com.example.library.service.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Aspect
@Component
public class AuditAspect {

    private static final Logger log =
            LoggerFactory.getLogger("AUDIT_LOG");

    /* ---------- BOOK ---------- */

    @AfterReturning(
        value = "execution(* com.example.library.service.BookService.createBook(..))",
        returning = "result"
    )
    public void auditCreateBook(Object result) {
        logAudit(AuditAction.CREATE_BOOK, extractBookId(result));
    }

    @AfterReturning(
        value = "execution(* com.example.library.service.BookService.catalog(..)) && args(bookId)",
        argNames = "bookId"
    )
    public void auditCatalogBook(UUID bookId) {
        logAudit(AuditAction.CATALOG_BOOK, bookId);
    }

    /* ---------- LOAN ---------- */

    @AfterReturning(
        value = "execution(* com.example.library.service.LoanService.borrow(..)) && args(bookId, username)",
        argNames = "bookId,username"
    )
    public void auditBorrowBook(UUID bookId, String username) {
        logAudit(AuditAction.BORROW_BOOK, bookId, username);
    }

    @AfterReturning(
        value = "execution(* com.example.library.service.LoanService.returnBook(..)) && args(bookId, username)",
        argNames = "bookId,username"
    )
    public void auditReturnBook(UUID bookId, String username) {
        logAudit(AuditAction.RETURN_BOOK, bookId, username);
    }

    /* ---------- HELPERS ---------- */

    private void logAudit(AuditAction action, UUID bookId) {
        String username = resolveUsername();
        log.info(
            "AUDIT action={} username={} bookId={}",
            action,
            username,
            bookId
        );
    }

    private void logAudit(AuditAction action, UUID bookId, String username) {
        log.info(
            "AUDIT action={} username={} bookId={}",
            action,
            username,
            bookId
        );
    }

    private UUID extractBookId(Object result) {
        try {
            return (UUID) result.getClass()
                    .getMethod("getId")
                    .invoke(result);
        } catch (Exception e) {
            return null;
        }
    }

    private String resolveUsername() {
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();
        return auth != null ? auth.getName() : "SYSTEM";
    }
}
