;************************************************************************
;                                                                       *
;   Filename:      BA_L6-Flash_LED_xtal-12F509.asm                  *
;   Date:          02/11/2018                                           *
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
;   Description:    Lesson 6, p. 4b-7a                                  *
;                                                                       *
;   Demonstrates use of Timer0 in counter mode                          *
;                                                                       *
;   LED flashes at 1 Hz (50% duty cycle),                               *
;   with timing derived from 32.768 kNz input on T0CKI                  *
;                                                                       *
;************************************************************************
;                                                                       *
;   Pin assignments:                                                    *
;       GP1 = flashing indicator LED                                    *
;       GP2 = external 32.768 kHz external clock input                  *
;             JP13->out, JP22->in on Goolibum training board            *
;                                                                       *
;************************************************************************


;	list     p=12F509             ; list directive to define processor
	#include <p12F509.inc>        ; processor specific variable definitions
	
;	EXTERN   delay10xW_R          ; W x 10 mS delay

;***** CONFIGURATION ************************************************************
	; ext reset, no code protect, no watchdog, int RC clock
	__CONFIG   _MCLRE_ON & _CP_OFF & _WDT_OFF & _IntRC_OSC

; '__CONFIG' directive is used to embed configuration word within .asm file.
; The lables following the directive are located in the respective .inc file. 
; See respective data sheet for additional information on configuration word.

;***** PIN ASSIGNMENTS
	#define    FLASH_LED GPIO,0   ; GP1 assigned to flashing LED

;***** VARIABLE DEFINITIONS *****************************************************
        UDATA
sTMR0   res 2                         ; TMR0 shadow register
	
;***** RC CALIBRATION **********************************************************
RCCAL   CODE    0x3FF                 ; processor reset vector
        res 1                         ; holds internal RC cal value, as a movlw k

;***** RESET VECTOR ************************************************************
RESET	CODE    0x000                 ; effective reset vector
	movwf   OSCCAL                ; apply internal RC factory calibration
	pagesel start
	goto    start                 ; jump to main code
	
;***** SUBROUTINE VECTORS ******************************************************
;;;delay10xW                         ; W x 10 mS delay
	;;;pagesel delay10xW_R
	;;;goto    delay10xW_R
		
;***** MAIN PROGRAM ************************************************************
MAIN    CODE
	
;***** Initialization
start	
        ; configure ports
	;;;movlw   b'111101'         ; configure GP1 as output
	movlw   0<<GP0|0<<GP1|1<<GP2 ; configure GP1 as output ... more readable
	                             ;   GP0 as output to test changing #define
				     ;   GP2 as input; JP13 MUST be out as there
				     ;     there is 32k, 50% square wave present
				     ;     that makes the LED appear on continuously
	tris    GPIO
	;;; configure timer
        ;;;movlw   b'11110110'       ; configure Timer0
	;;;        ; --1-----            counter mode (TOCS = 1)
	;;;	   ; ----0---            assign prescaler to Timer0 (PSA = 0)
	;;;	   ; -----110            set prescaler to 1:128 (PS<2:0> = 110)
	;;;option                    ;   -> increment every 128 uS

	; configure timer
	movlw   1<<T0CS | 0<PSA | b'110' ; more reliable (perhaps readable, too)
	option
	
	; turn off LED
	banksel sTMR0
	clrf    GPIO
	
;***** Main loop
main_loop
	; set GP1 to current value of TMR0<7>
        movf    TMR0,W            ; get the current TMR0 value
	movwf   sTMR0
	btfss   sTMR0,7
	;;;bcf     GPIO,1            ; turn off the LED
	bcf     FLASH_LED         ; turn off the LED (more readable and maintainable)
	btfsc   sTMR0,7
	bsf     FLASH_LED         ; turn on the LED
	
	; repeat forever
	goto    main_loop         ; repeat forever

	END
