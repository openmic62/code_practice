;**********************************************************************
;   This file is a basic code template for object module code         *
;   generation on the PIC10F200. This file contains the               *
;   basic code building blocks to build upon.                         *
;                                                                     *
;   Refer to the MPASM User's Guide for additional information on     *
;   features of the assembler and linker.                             *
;                                                                     *
;   Refer to the respective PIC data sheet for additional             *
;   information on the instruction set.                               *
;                                                                     *
;                                                                     *
;**********************************************************************
;                                                                     *
;    Filename:	    xxx.asm                                           *
;    Date:                                                            *
;    File Version:                                                    *
;                                                                     *
;    Author:                                                          *
;    Company:                                                         *
;                                                                     * 
;                                                                     *
;**********************************************************************
;                                                                     *
;    Files required: P10F200.INC                                      *
;                                                                     *
;                                                                     *
;                                                                     *
;**********************************************************************
;                                                                     *
;    Notes:                                                           *
;                                                                     *
;                                                                     *
;                                                                     *
;                                                                     *
;**********************************************************************

	list      p=10F200            ; list directive to define processor
	#include <p10F200.inc>        ; processor specific variable definitions

	__CONFIG   _MCLRE_ON & _CP_OFF & _WDT_OFF

; '__CONFIG' directive is used to embed configuration word within .asm file.
; The lables following the directive are located in the respective .inc file. 
; See respective data sheet for additional information on configuration word.
	
;***** RC CALIBRATION **********************************************************
RCCAL   CODE    0x0FF             ; processor reset vector
        res 1                     ; holds internal RC cal value, as a movlw k

;***** RESET VECTOR ************************************************************
RESET	CODE    0x000             ; effective reset vector
	movwf   OSCCAL            ; apply internal RC factory calibration 

; Internal RC calibration value is placed at location 0xFF by Microchip
; as a movlw k, where the k is a literal value.
	
;***** MAIN PROGRAM ************************************************************
	
start	
	movlw   b'111101'         ; configure GP1 (only) as an output
	tris    GPIO
	movlw   b'000010'         ; set GP1 high
	movwf   GPIO
	
;***** Main loop
	goto $                    ; loop forever

	END                       ; directive 'end of program'

