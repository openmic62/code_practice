;************************************************************************
;                                                                       *
;   Filename:      BA_L5-Flash_LED_xtal_Rotate-12F509.asm                  *
;   Date:          02/07/2018                                           *
;   File Version:  1.1                                                  *
;                                                                       *
;   Author:        Mike Rocha                                           *
;   Company:       RoCo, Inc.                                           *
;                                                                       *
;************************************************************************
;                                                                       *
;   Architecture:  Baseline PIC                                         *
;   Processor:     12F508/509                                           *
;                                                                       *
;************************************************************************
;                                                                       *
;   Files required:                                                     *
;                                                                       *
;************************************************************************
;                                                                       *
;   Description:    Lesson 5, p. 13b-17b                                *
;                                                                       *
;   Demonstrates use of Timer0 in counter mode                          *
;                                                                       *
;   LED flashes at 1 Hz (50% duty cycle),                               *
;   with timing derived from 32.768 kNz input on T0CKI                  *
;                                                                       *
;************************************************************************
;                                                                       *
;   Pin assignments:                                                    *
;       GP1 = flashing LED                                              *
;       GP2 = external 32.768 kHz external clock input                  *
;                                                                       *
;************************************************************************


;	list     p=12F509             ; list directive to define processor
	#include <p12F509.inc>        ; processor specific variable definitions
	
;	EXTERN   delay10xW_R          ; W x 10 mS delay

	;***** CONFIGURATION
	; ext reset, no code protect, no watchdog, int RC clock
	__CONFIG   _MCLRE_OFF & _CP_OFF & _WDT_OFF & _IntRC_OSC

; '__CONFIG' directive is used to embed configuration word within .asm file.
; The lables following the directive are located in the respective .inc file. 
; See respective data sheet for additional information on configuration word.

;***** VARIABLE DEFINITIONS *****************************************************
        UDATA
temp    res 1                     ; temp register for rlf command
	
;***** RC CALIBRATION **********************************************************
RCCAL   CODE    0x3FF             ; processor reset vector
        res 1                     ; holds internal RC cal value, as a movlw k

;***** RESET VECTOR ************************************************************
RESET	CODE    0x000             ; effective reset vector
	movwf   OSCCAL            ; apply internal RC factory calibration
	pagesel start
	goto    start             ; jump to main code
	
;***** SUBROUTINE VECTORS ******************************************************
;;;delay10xW                         ; W x 10 mS delay
	;;;pagesel delay10xW_R
	;;;goto    delay10xW_R
		
;***** MAIN PROGRAM ************************************************************
MAIN    CODE
	
;***** Initialization
start	
        ; configure ports
	movlw   b'111101'         ; configure GP1 as output
	tris    GPIO
	; configure timer
        movlw   b'11110110'       ; configure Timer0
	        ; --1-----            counter mode (TOCS = 1)
		; ----0---            assign prescaler to Timer0 (PSA = 0)
		; -----110            set prescaler to 1:128 (PS<2:0> = 110)
	option                    ;   -> increment every 128 uS
	
	; turn off LED
	banksel temp
	clrf    GPIO
	
;***** Main loop
main_loop
	; set GP1 to current value of TMR0<7>
        rlf     TMR0,W            ; rotate TMR0 value into W; TMRO<7> in C
	rlf     temp,F            ; rotate W into temp; TMRO<7> in GP0
	rlf     temp,W            ; rotate temp into W; TMR0<7> in GP1
        movwf   GPIO
	
	; repeat forever
	goto    main_loop         ; repeat forever

	END
